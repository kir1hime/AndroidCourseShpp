package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.entity.ContactItem
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.utils.isContainsOrderedSequence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Stack
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
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
            if (contact.name.isContainsOrderedSequence(input)) {
                filteredContactList.add(contact)
            }
        }
        _filteredContactList.value = filteredContactList
    }

    private fun deleteContactItem(contactItem: ContactItem) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                contactsRepository.deleteContactItem(contactItem)
                val contactList = contactsRepository.loadContacts()
                setState { copy(contactList = contactList) }
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

        deletedItems.push(contactItem)
    }

    private fun deleteListOfContactItems(contactItems: List<ContactItem>) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                contactsRepository.deleteContactItems(contactItems)
                val contactList = contactsRepository.loadContacts()
                setState { copy(contactList = contactList) }
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
                contactsRepository.addContactItem(contactItem)
                val contactList = contactsRepository.loadContacts()
                setState { copy(contactList = contactList) }
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
        deletedItems.pop()
        if (!deletedItems.isEmpty()) {
            val deletedItem = deletedItems.peek()
            setEffect(ContactListContract.Effect.ShowUndoDeletingItemSnackBar(deletedItem))
        }
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
                val contactList = contactsRepository.loadContacts()
                setState { copy(contactList = contactList) }
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

}

