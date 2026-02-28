package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist


import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.GetContactsUseCase
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.notifications.NotificationAction
import com.example.androidcourseshpp.ui.notifications.NotificationService
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.ContactItem
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.toContactItem
import com.example.androidcourseshpp.ui.utils.isContainsOrderedSequence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Stack
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val deleteContactsUseCase: DeleteContactsUseCase,
    private val getContactsUseCase: GetContactsUseCase,
    private val notificationService: NotificationService
) : BaseViewModel<ContactListContract.Event, ContactListContract.Effect, ContactListContract.UIState>() {

    override fun initState() = ContactListContract.UIState(
        contactList = emptyList(),
        isProgressBarShowed = false,
        isTryAgainButtonShowed = false,
        isSearchMode = false
    )

    val deletedItems = Stack<ContactItem>()
    private val _filteredContactList = MutableStateFlow(emptyList<ContactItem>())
    val filteredContactList: StateFlow<List<ContactItem>> get() = _filteredContactList

    init {
        loadContacts()
    }


    override fun handleEvent(event: ContactListContract.Event) {
        when (event) {
            is ContactListContract.Event.SearchModeSwitched -> switchSearchMode(event.isSearchModeEnabled)
            is ContactListContract.Event.OnHideSearchButtonCLicked -> hideSearchBar()
            is ContactListContract.Event.OnSearchButtonClicked -> showSearchBar()
            is ContactListContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is ContactListContract.Event.LoadContactList -> loadContacts()
            is ContactListContract.Event.OnSearchBarTextChanged -> updateFilteredContactListBy(event.input)
            is ContactListContract.Event.ContactItemDeleted -> deleteContact(event.contactItem)
            is ContactListContract.Event.OnAddContactClicked -> {
                switchSearchMode(false)
                navigateToAddContactsScreen()
            }

            is ContactListContract.Event.ContactItemAdded -> addContact(
                event.contactItem
            )

            is ContactListContract.Event.OnDeleteSelectedItemsFloatingButtonClicked -> deleteListOfContacts(
                event.contactItems
            )

            is ContactListContract.Event.OnItemClicked -> navigateToDetailsScreen(
                event.contact
            )
        }
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
        val filteredContactList = mutableListOf<ContactItem>()
        state.value.contactList.forEach { contact ->
            if (contact.name.isContainsOrderedSequence(input)) {
                filteredContactList.add(contact)
            }
        }
        _filteredContactList.value = filteredContactList
    }

    private fun deleteContact(contactItem: ContactItem) {
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                deleteContactUseCase(contactItem.id)

            },
            onSuccess = {
                setState { copy(contactList = contactList) }

                updateFilteredContactList { list ->
                    list.remove(contactItem)
                }
                notificationService.showContactDeletedNotification(
                    userInfo = contactItem.toContactDetails(),
                    notificationActionId = NotificationAction.DELETE_CONTACT.ordinal
                )
            },
            onLocalStorageError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )

        deletedItems.push(contactItem)
    }

    private fun deleteListOfContacts(contactItems: List<ContactItem>) {
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                deleteContactsUseCase(contactItems.map { it.id })
            },
            onSuccess = {
                setState { copy(contactList = contactList) }
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

    private fun addContact(contactItem: ContactItem) {
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                addContactUseCase(contactInfo = contactItem.toContactInfo())

            },
            onSuccess = {
                setState { copy(contactList = contactList) }
                updateFilteredContactList { list ->
                    list.add(contactItem)
                }
                notificationService.showContactAddedNotification(
                    userInfo = contactItem.toContactDetails(),
                    notificationActionId = NotificationAction.ADD_CONTACT.ordinal
                )

                deletedItems.pop()
                if (!deletedItems.isEmpty()) {
                    val deletedItem = deletedItems.peek()
                    setEffect(ContactListContract.Effect.ShowUndoDeletingItemSnackBar(deletedItem))
                }
            },
            onLocalStorageError = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun loadContacts() {
        setState {
            copy(
                isProgressBarShowed = true,
                isTryAgainButtonShowed = false
            )
        }
        viewModelScope.launch {
            getContactsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        setState { copy(isProgressBarShowed = false) }
                        val contactList =
                            result.data.filter { syncContact -> syncContact.syncState != SyncAction.DELETED }
                                .map { syncContact -> syncContact.contactInfo.toContactItem() }

                        setState { copy(contactList = contactList) }

                        updateFilteredContactList { list ->
                            list.clear()
                            list.addAll(contactList)
                        }
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
        _filteredContactList.update {
            val newFilteredList = _filteredContactList.value.toMutableList()
            toUpdate(newFilteredList)
            newFilteredList
        }
    }

}

