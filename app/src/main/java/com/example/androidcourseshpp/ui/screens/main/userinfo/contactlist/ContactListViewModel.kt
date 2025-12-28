package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist

import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.data.models.contactlist.ContactsRepository
import com.example.androidcourseshpp.data.models.contactlist.ContactItem
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Stack
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : BaseViewModel<ContactListContract.Event, ContactListContract.Effect, ContactListContract.UIState>() {

    override fun initState() = ContactListContract.UIState(emptyList(), false)
    val deletedItems = Stack<Pair<ContactItem, Int>>()

    init {
        viewModelScope.launch {
            loadContacts()
            contactsRepository.contactList.collect { contactList ->
                setState { copy(contactList = contactList) }
            }
        }
    }


    override fun handleEvent(event: ContactListContract.Event) {
        when (event) {
            is ContactListContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is ContactListContract.Event.OnAddContactClicked -> navigateToAddContactsScreen()
            is ContactListContract.Event.ContactItemAdded -> addContactItem(
                event.contactItem
            )

            is ContactListContract.Event.ContactItemDeleted -> deleteContactItem(
                event.contactItem,
                event.position
            )

            is ContactListContract.Event.OnDeleteSelectedItemsFloatingButtonClicked -> deleteListOfContactItems(
                event.contactItems
            )

            is ContactListContract.Event.OnItemClicked -> navigateToDetailsScreen(
                event.contact
            )
        }
    }

    private fun deleteContactItem(contactItem: ContactItem, position: Int) {
        processNetworkExceptions(
            toExecute = {
                contactsRepository.deleteContactItem(contactItem)
            },
            processBackendException = {},
            processResponseProcessingException = {},
            processConnectionException = {},
            finally = { }
        )

        deletedItems.push(Pair(contactItem, position))
    }

    private fun deleteListOfContactItems(contactItems: List<ContactItem>) {
        processNetworkExceptions(
            toExecute = {
                contactsRepository.deleteContactItems(contactItems)
            },
            processBackendException = {},
            processResponseProcessingException = {},
            processConnectionException = {},
            finally = { })
    }

    private fun addContactItem(contactItem: ContactItem) {
        processNetworkExceptions(
            toExecute = {
                contactsRepository.addContactItem(contactItem)
            },
            processBackendException = {},
            processResponseProcessingException = {},
            processConnectionException = {},
            finally = { })
    }

    private fun loadContacts() {
        processNetworkExceptions(
            toExecute = {
                contactsRepository.initContactList()
            },
            processBackendException = {},
            processConnectionException = {},
            processResponseProcessingException = {},
            finally = {}
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

