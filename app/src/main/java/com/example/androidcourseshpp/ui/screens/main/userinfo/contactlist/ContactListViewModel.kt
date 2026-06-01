package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.GetContactsUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.ContactItem
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.toContactItem
import com.example.androidcourseshpp.ui.utils.containsOrderedSequence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Stack
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val deleteContactsUseCase: DeleteContactsUseCase,
    private val getContactsUseCase: GetContactsUseCase
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
            is ContactListContract.Event.ContactItemDeleted -> deleteContactItem(event.contactItem)
            is ContactListContract.Event.OnAddContactClicked -> {
                switchSearchMode(false)
                navigateToAddContactsScreen()
            }

            is ContactListContract.Event.ContactItemAdded -> addContactItem(
                event.contactItem
            )

            is ContactListContract.Event.OnDeleteSelectedItemsFloatingButtonClicked -> deleteListOfContactItems(
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
            if (contact.name.containsOrderedSequence(input)) {
                filteredContactList.add(contact)
            }
        }
        _filteredContactList.value = filteredContactList
    }

    private fun deleteContactItem(contactItem: ContactItem) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }

                deleteContactUseCase(contactItem.id)
                val contactList = getContactsUseCase().map { it.toContactItem() }

                setState { copy(contactList = contactList) }

                updateFilteredContactList { list ->
                    list.remove(contactItem)
                }

                deletedItems.push(contactItem)
            },
            processBackendException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )

    }

    private fun deleteListOfContactItems(contactItems: List<ContactItem>) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }

                deleteContactsUseCase(contactItems.map { it.toContactInfo() })
                val contactList = getContactsUseCase().map { it.toContactItem() }

                setState { copy(contactList = contactList) }
                updateFilteredContactList { list ->
                    list.removeAll(contactItems)
                }
            },
            processBackendException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun addContactItem(contactItem: ContactItem) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }

                addContactUseCase(newContactId = contactItem.id)
                val contactList = getContactsUseCase().map { it.toContactItem() }

                setState { copy(contactList = contactList) }
                updateFilteredContactList { list ->
                    list.add(contactItem)
                }

                deletedItems.pop()
                if (!deletedItems.isEmpty()) {
                    val deletedItem = deletedItems.peek()
                    setEffect(ContactListContract.Effect.ShowUndoDeletingItemSnackBar(deletedItem))
                }
            },
            processBackendException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun loadContacts() {
        processNetworkExceptions(
            toExecute = {
                setState {
                    copy(
                        isProgressBarShowed = true,
                        isTryAgainButtonShowed = false,
                        contactList = emptyList()
                    )
                }
                val contactList = getContactsUseCase().map { it.toContactItem() }
                setState { copy(contactList = contactList) }
                updateFilteredContactList { list ->
                    list.clear()
                    list.addAll(contactList)
                }
            },
            processBackendException = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(ContactListContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
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

