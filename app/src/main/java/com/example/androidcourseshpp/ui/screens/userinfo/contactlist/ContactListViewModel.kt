package com.example.androidcourseshpp.ui.screens.userinfo.contactlist

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.contactlistdata.ContactListGenerator
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.data.contactlistdata.SelectableContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.util.Stack
import javax.inject.Inject

private const val NEW_CONTACT_AVATAR =
    "https://kartinki.pics/uploads/posts/2022-02/1645235615_4-kartinkin-net-p-kroliki-kartinki-4.jpg"

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contentResolver: ContentResolver
) : ViewModel() {

    private var mutableContactList =
        MutableStateFlow(ContactListGenerator(contentResolver).getContactItems())
    val contactList: StateFlow<List<ContactItem>> = mutableContactList.asStateFlow()

    val selectableContactItems = contactList.map { contacts ->
        contacts.map { contact ->
            SelectableContactItem(
                contact,
                false
            )
        }
    }
    private var isPhoneContactsAdded = false

    val deletedItems = Stack<Pair<ContactItem, Int>>()

    private var _toShowUndoPreviousDeleting: Boolean = false
    val toShowUndoPreviousDeleting get() = _toShowUndoPreviousDeleting


    fun resetContactItemsSelection() {
        mutableContactList.value = mutableContactList.value.map { item -> item.copy() }
    }

    fun addPhoneContacts() {
        if (!isPhoneContactsAdded) {
            val currentContactList = mutableContactList.value.toMutableList()
            val lastContactItemId = currentContactList[currentContactList.lastIndex].id

            val contactItemsFromPhoneContacts = ContactListGenerator(
                contentResolver
            ).getContactItemsFromPhoneContacts(lastContactItemId)

            currentContactList.addAll(contactItemsFromPhoneContacts)

            mutableContactList.value = currentContactList
            isPhoneContactsAdded = true
        }
    }

    fun createNewContact(contactName: String?, contactCareer: String?): ContactItem {
        val contactList = mutableContactList.value
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
            addContactItem(newContact, mutableContactList.value.size)
        }
    }

    fun deleteContactItem(contactItem: ContactItem, position: Int) {
        updateContactList { it.remove(contactItem) }
        deletedItems.push(Pair(contactItem, position))
    }

    fun deleteListOfContactItems(contactItems: List<ContactItem>) {
        updateContactList { it.removeAll(contactItems) }
    }

    fun addContactItem(contactItem: ContactItem, position: Int) {
        updateContactList { it.add(position, contactItem) }
        deletedItems.pop()
    }

    private fun isNewContactDataIsBlank(contactItem: ContactItem): Boolean {
        return contactItem.career.isBlank() || contactItem.name.isBlank()
    }

    private fun updateContactList(operation: (MutableList<ContactItem>) -> Unit) {
        val contactList = mutableContactList.value.toMutableList()

        operation.invoke(contactList)
        mutableContactList.value = contactList
    }
}

