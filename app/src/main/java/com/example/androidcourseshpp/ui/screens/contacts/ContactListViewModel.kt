package com.example.androidcourseshpp.ui.screens.contacts


import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.contactlistdata.ContactListGenerator
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

class ContactListViewModel(
    private val contentResolver: ContentResolver,
    isAccessToContactsAllowed: Boolean
) : ViewModel() {

    private var mutableContactList = MutableLiveData<List<ContactItem>>()
    val contactList: LiveData<List<ContactItem>> get() = mutableContactList

    init {
        mutableContactList.value =
            ContactListGenerator(contentResolver, isAccessToContactsAllowed).getContactItems()
    }

    fun updateContactList(isAccessToContactsAllowed : Boolean) {
        mutableContactList.value =
            ContactListGenerator(contentResolver, isAccessToContactsAllowed).getContactItems()
    }

    fun deleteContactItem(contactItem: ContactItem) {
        updateContactList { it.remove(contactItem) }
    }

    fun deleteContactItem(position: Int) {
        updateContactList { it.removeAt(position) }
    }

    fun addContactItem(contactItem: ContactItem, position: Int) {
        updateContactList { it.add(position, contactItem) }
    }

    fun isNewContactDataIsBlank(contactItem: ContactItem): Boolean {
        return contactItem.career.isBlank() || contactItem.name.isBlank()
    }

    private fun updateContactList(operation: (MutableList<ContactItem>) -> Unit) {
        mutableContactList.value?.toMutableList()?.let {
            operation.invoke(it)
            mutableContactList.value = it
        }
    }

}