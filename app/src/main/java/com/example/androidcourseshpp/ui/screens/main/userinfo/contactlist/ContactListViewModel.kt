package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.models.contactlist.ContactsRepository
import com.example.androidcourseshpp.data.models.contactlist.ContactItem
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Stack
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : BaseViewModel<ContactListContract.Event, ContactListContract.Effect, ContactListContract.UIState>() {

    override fun initState() = ContactListContract.UIState(
        contactList = emptyList(),
        isProgressBarShowed = false,
        isTryAgainButtonShowed = false
    )

    val deletedItems = Stack<ContactItem>()

    init {
        loadContacts()
    }


    override fun handleEvent(event: ContactListContract.Event) {
        when (event) {
            is ContactListContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is ContactListContract.Event.LoadContactList -> loadContacts()
            is ContactListContract.Event.OnAddContactClicked -> navigateToAddContactsScreen()
            is ContactListContract.Event.ContactItemAdded -> addContactItem(
                event.contactItem
            )

            is ContactListContract.Event.ContactItemDeleted -> deleteContactItem(event.contactItem)

            is ContactListContract.Event.OnDeleteSelectedItemsFloatingButtonClicked -> deleteListOfContactItems(
                event.contactItems
            )

            is ContactListContract.Event.OnItemClicked -> navigateToDetailsScreen(
                event.contact
            )
        }
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
        setEffect(ContactListContract.Effect.NavigateToAddContactsScreen)
    }
}

