package com.example.androidcourseshpp.ui.screens.userinfo.contactlist

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.contactlistdata.ContactsRepository
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Stack
import javax.inject.Inject

private const val NEW_CONTACT_AVATAR =
    "https://kartinki.pics/uploads/posts/2022-02/1645235615_4-kartinkin-net-p-kroliki-kartinki-4.jpg"

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    val contactList: StateFlow<List<ContactItem>> = contactsRepository.contactList
    private var isPhoneContactsAdded = false

    var deletedItems = Stack<Pair<ContactItem, Int>>()
        private set

    fun addPhoneContacts() {
        if (!isPhoneContactsAdded) {
            val currentContactList = contactList.value.toMutableList()
            val lastContactItemId = currentContactList[currentContactList.lastIndex].id

            val contactItemsFromPhoneContacts =
                contactsRepository.getContactItemsFromPhoneContacts(lastContactItemId)

            contactsRepository.addContactItems(contactItemsFromPhoneContacts)
            isPhoneContactsAdded = true
        }
    }

    fun createNewContact(contactName: String?, contactCareer: String?): ContactItem {
        val contactList = contactList.value
        val lastId = contactList[contactList.size - 1].id

        val newContact = ContactItem(
            lastId + 1,
            contactName ?: "",
            contactCareer ?: "",
            NEW_CONTACT_AVATAR
        )

        return newContact
    }

    fun processAddContactDialogEvent(newContact: ContactItem) {
        if (!isNewContactDataIsBlank(newContact)) {
            addContactItem(newContact, contactList.value.size)
        }
    }

    fun deleteContactItem(contactItem: ContactItem, position: Int) {
        contactsRepository.deleteContactItem(contactItem)
        deletedItems.push(Pair(contactItem, position))
    }

    fun deleteListOfContactItems(contactItems: List<ContactItem>) {
        contactsRepository.deleteContactItems(contactItems)
    }

    fun addContactItem(contactItem: ContactItem, position: Int) {
        contactsRepository.addContactItem(contactItem, position)
    }

    private fun isNewContactDataIsBlank(contactItem: ContactItem): Boolean {
        return contactItem.career.isBlank() || contactItem.name.isBlank()
    }
}

