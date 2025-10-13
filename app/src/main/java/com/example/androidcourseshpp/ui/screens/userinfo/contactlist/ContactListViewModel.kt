package com.example.androidcourseshpp.ui.screens.userinfo.contactlist

import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.data.contactlist.ContactsRepository
import com.example.androidcourseshpp.data.contactlist.ContactItem
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Stack
import javax.inject.Inject

private const val NEW_CONTACT_AVATAR =
    "https://kartinki.pics/uploads/posts/2022-02/1645235615_4-kartinkin-net-p-kroliki-kartinki-4.jpg"

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : BaseViewModel<ContactListContract.Event, ContactListContract.Effect, ContactListContract.UIState>() {

    override fun initState() = ContactListContract.UIState(emptyList(), false)
    val deletedItems = Stack<Pair<ContactItem, Int>>()

    init {
        viewModelScope.launch {
            contactsRepository.contactList.collect { list ->
                setState { copy(list) }
            }
        }
    }

    override fun handleEvent(event: ContactListContract.Event) {
        when (event) {
            is ContactListContract.Event.ContactItemAdded -> addContactItem(
                event.contactItem,
                event.position
            )

            is ContactListContract.Event.ContactItemDeleted -> deleteContactItem(
                event.contactItem,
                event.position
            )

            is ContactListContract.Event.OnDeleteSelectedItemsFloatingButtonClicked -> deleteListOfContactItems(
                event.contactItems
            )

            is ContactListContract.Event.AddContactDialogEventProcessed -> processAddContactDialogEvent(
                event.contactName,
                event.contactCareer
            )

            is ContactListContract.Event.OnItemClicked -> navigateToDetailsScreen(
                event.contact,
                event.avatar
            )

            is ContactListContract.Event.PhoneContactsAdded -> addPhoneContacts()

            is ContactListContract.Event.OnArrowBackButtonClickLed -> navigateToPreviousScreen()
        }
    }

    private fun addPhoneContacts() {
        if (!state.value.isPhoneContactsLoaded) {
            val lastContactItemId = state.value.contactList.last().id
            val contactItemsFromPhoneContacts =
                contactsRepository.getContactItemsFromPhoneContacts(lastContactItemId)
            contactsRepository.addContactItems(contactItemsFromPhoneContacts)
            setState {
                copy(isPhoneContactsLoaded = true)
            }
        }
    }

    private fun processAddContactDialogEvent(newContactName: String, newContactCareer: String) {
        val newContact = createNewContact(newContactName, newContactCareer)
        if (!isNewContactDataIsBlank(newContact)) {
            addContactItem(newContact, state.value.contactList.size)
        }
    }

    private fun createNewContact(contactName: String, contactCareer: String): ContactItem {
        val lastContactItemId = state.value.contactList.last().id

        val newContact = ContactItem(
            lastContactItemId + 1,
            contactName,
            contactCareer,
            NEW_CONTACT_AVATAR
        )

        return newContact
    }

    private fun deleteContactItem(contactItem: ContactItem, position: Int) {
        contactsRepository.deleteContactItem(contactItem)
        deletedItems.push(Pair(contactItem, position))
    }

    private fun deleteListOfContactItems(contactItems: List<ContactItem>) {
        contactsRepository.deleteContactItems(contactItems)
    }

    private fun addContactItem(contactItem: ContactItem, position: Int) {
        contactsRepository.addContactItem(contactItem, position)
    }

    private fun isNewContactDataIsBlank(contactItem: ContactItem): Boolean {
        return contactItem.career.isBlank() || contactItem.name.isBlank()
    }

    private fun navigateToDetailsScreen(contact: ContactItem, avatar: ImageView) {
        setEffect(ContactListContract.Effect.NavigateToDetailsScreen(contact, avatar))
    }

    private fun navigateToPreviousScreen() {
        setEffect(ContactListContract.Effect.NavigateToMyProfileScreen)
    }
}

