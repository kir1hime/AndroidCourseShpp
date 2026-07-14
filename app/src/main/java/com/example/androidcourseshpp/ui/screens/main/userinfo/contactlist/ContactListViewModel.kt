package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist


import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.GetContactsUseCase
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsFromRemoteUseCase
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.notifications.NotificationAction
import com.example.androidcourseshpp.ui.notifications.NotificationService
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.ContactItem
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.toContactItem
import com.example.androidcourseshpp.ui.sync.ContactsSyncScheduler
import com.example.androidcourseshpp.ui.utils.containsOrderedSequence
import com.example.androidcourseshpp.ui.utils.executeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Stack
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val addContactsUseCase: AddContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val deleteContactsUseCase: DeleteContactsUseCase,
    private val getContactsUseCase: GetContactsUseCase,
    private val notificationService: NotificationService,
    private val contactListSyncScheduler: ContactsSyncScheduler,
    private val syncContactsFromRemoteUseCase: SyncContactsFromRemoteUseCase
) : BaseViewModel<ContactListContract.Event, ContactListContract.Effect, ContactListContract.UIState>() {

    override fun initState() = ContactListContract.UIState(
        contactList = emptyList(),
        isProgressBarShowed = false,
        isTryAgainButtonShowed = false,
        isSearchMode = false,
        isSelectMode = false
    )

    val deletedContacts = Stack<ContactItem>()
    private val deletedContactsInMultiselectMode = mutableListOf<ContactItem>()
    private val _filteredContactList = MutableStateFlow(emptyList<ContactItem>())
    val filteredContactList: StateFlow<List<ContactItem>> get() = _filteredContactList

    private val contactsLoadingTrigger = MutableSharedFlow<Unit>(replay = 1)

    init {
        loadContacts()
        triggerContactsLoading()
        contactListSyncScheduler.executeOnceSyncFromRemote()
    }


    override fun handleEvent(event: ContactListContract.Event) {
        when (event) {
            is ContactListContract.Event.OnSearchModeSwitched -> switchSearchMode(event.isSearchModeEnabled)
            is ContactListContract.Event.OnHideSearchButtonClicked -> hideSearchBar()
            is ContactListContract.Event.OnReloadContacts -> reloadContacts()
            is ContactListContract.Event.OnSearchButtonClicked -> showSearchBar()
            is ContactListContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is ContactListContract.Event.OnSelectModeChange -> changeSelectMode(event.isSelectMode)
            is ContactListContract.Event.OnSearchBarTextChanged -> updateFilteredContactListBy(event.input)
            is ContactListContract.Event.OnContactItemDeleted -> deleteContact(event.contactItem)
            is ContactListContract.Event.OnAddContactClicked -> {
                switchSearchMode(false)
                navigateToAddContactsScreen()
            }

            is ContactListContract.Event.OnTryAgainButtonClicked -> {
                triggerContactsLoading()
                setState { copy(isTryAgainButtonShowed = false) }
            }

            is ContactListContract.Event.OnGetBackDeletedContact -> getBackDeletedContact(
                event.contactItem
            )

            is ContactListContract.Event.OnGetBackDeletedContacts -> getBackDeletedContacts()

            is ContactListContract.Event.OnDeleteSelectedItemsFloatingButtonClicked -> deleteListOfContacts(
                event.contactItems
            )

            is ContactListContract.Event.OnItemClicked -> navigateToDetailsScreen(
                event.contact
            )
        }
    }

    private fun reloadContacts() {
        executeUseCase(
            toExecute = {
                syncContactsFromRemoteUseCase()
            },
            onRemoteError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            onConnectionError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.connection_error))
            },
            onLocalStorageError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { setEffect(ContactListContract.Effect.HideRefreshProgressBar) }
        )
    }

    private fun changeSelectMode(isSelectMode: Boolean) {
        setState { copy(isSelectMode = isSelectMode) }
    }

    private fun switchSearchMode(isSearchMode: Boolean) {
        setState { copy(isSearchMode = isSearchMode) }
    }

    private fun hideSearchBar() {
        setEffect(ContactListContract.Effect.HideSearchBar)
    }

    private fun showSearchBar() {
        setEffect(ContactListContract.Effect.ShowSearchBar)
    }

    private fun updateFilteredContactListBy(input: String) {
        updateFilteredContactList { list -> list.clear() }
        state.value.contactList.forEach { contact ->
            if (contact.name.containsOrderedSequence(input)) {
                updateFilteredContactList { list -> list.add(contact) }
            }
        }
    }

    private fun deleteContact(contactItem: ContactItem) {
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                deleteContactUseCase(contactItem.id)

            },
            onSuccess = {
                contactListSyncScheduler.executeOnceSyncToRemote()
                updateFilteredContactList { list ->
                    list.remove(contactItem)
                }
                notificationService.showContactDeletedNotification(
                    userInfo = contactItem.toContactDetails(),
                    notificationActionId = NotificationAction.DELETE_CONTACT.ordinal
                )
                deletedContacts.push(contactItem)
            },
            onLocalStorageError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun deleteListOfContacts(contactItems: List<ContactItem>) {
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                deleteContactsUseCase(contactItems.map { it.id })
            },
            onSuccess = {
                deletedContactsInMultiselectMode.clear()
                deletedContactsInMultiselectMode.addAll(contactItems.toList())
                contactListSyncScheduler.executeOnceSyncToRemote()
                updateFilteredContactList { list ->
                    list.removeAll(contactItems)
                }
            },
            onLocalStorageError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun getBackDeletedContact(contactItem: ContactItem) {
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                addContactUseCase(contactInfo = contactItem.toContactInfo())

            },
            onSuccess = {
                contactListSyncScheduler.executeOnceSyncToRemote()

                updateFilteredContactList { list ->
                    list.add(contactItem)
                }

                deletedContacts.pop()
                if (!deletedContacts.isEmpty()) {
                    val deletedItem = deletedContacts.peek()
                    setEffect(ContactListContract.Effect.ShowUndoDeletingItemSnackBar(deletedItem))
                }
            },
            onLocalStorageError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun getBackDeletedContacts() {
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                addContactsUseCase(
                    contacts = deletedContactsInMultiselectMode.map { it.toContactInfo() })
            }, onSuccess = {
                contactListSyncScheduler.executeOnceSyncToRemote()
                updateFilteredContactList { list ->
                    list.addAll(deletedContactsInMultiselectMode)
                }
                deletedContactsInMultiselectMode.clear()
            },
            onLocalStorageError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }

        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadContacts() {
        setState {
            copy(
                isProgressBarShowed = true,
                isTryAgainButtonShowed = false
            )
        }
        viewModelScope.launch {
            contactsLoadingTrigger.flatMapLatest {
                getContactsUseCase()
            }.collect { result ->

                when (result) {
                    is Result.Success -> {
                        setState { copy(isProgressBarShowed = false) }

                        val contactList =
                            result.data.filter { syncContact -> syncContact.syncState != SyncAction.DELETED }
                                .map { syncContact -> syncContact.contactInfo.toContactItem() }

                        setState { copy(contactList = contactList) }
                    }

                    is Result.Error -> {
                        setState {
                            copy(
                                isTryAgainButtonShowed = true,
                                isProgressBarShowed = false
                            )
                        }
                        when (result.error) {
                            AppError.ConnectionError -> setEffect(
                                ContactListContract.Effect.ShowToast(
                                    R.string.connection_error
                                )
                            )

                            else -> setEffect(
                                ContactListContract.Effect.ShowToast(
                                    R.string.generic_error
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun triggerContactsLoading() {
        viewModelScope.launch {
            contactsLoadingTrigger.emit(Unit)
        }
    }

    private fun navigateToDetailsScreen(contact: ContactItem) {
        setEffect(ContactListContract.Effect.NavigateToDetailsScreen(contact))
    }

    private fun navigateToPreviousScreen() {
        setEffect(ContactListContract.Effect.NavigateToUserProfileScreen)
    }

    private fun navigateToAddContactsScreen() {
        setEffect(ContactListContract.Effect.HideSearchBar)
        setEffect(ContactListContract.Effect.NavigateToAddContactsScreen)
    }

    private fun updateFilteredContactList(toUpdate: (MutableList<ContactItem>) -> Unit) {
        _filteredContactList.update { currentList ->
            val newFilteredList = currentList.toMutableList()
            toUpdate(newFilteredList)
            newFilteredList
        }
    }

}

