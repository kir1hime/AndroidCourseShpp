package com.example.androidcourseshpp.ui.screens.contacts


import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.contactlistdata.ContactListGenerator
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

class ContactListViewModel(
    contentResolver: ContentResolver,
    isAccessToContactsAllowed: Boolean
) : ViewModel() {

    private var mutableContactList = MutableLiveData<MutableList<ContactItem>>()
    val contactList: LiveData<MutableList<ContactItem>> get() = mutableContactList

    init {
        mutableContactList.value =
            ContactListGenerator(contentResolver, isAccessToContactsAllowed).getContactItems()
    }

    fun deleteContactItem(contactItem: ContactItem) {
        val contactList = mutableContactList.value!!.toMutableList()
        contactList.remove(contactItem)
        mutableContactList.value = contactList
        updateContactIds()
    }

    fun deleteContactItem(position: Int) {
        val contactList = mutableContactList.value!!.toMutableList()
        contactList.removeAt(position)
        mutableContactList.value = contactList
        updateContactIds()
    }

    private fun updateContactIds() {
        for (i in mutableContactList.value!!.indices) {
            mutableContactList.value!![i].id = i
        }
    }

    fun addContactItem(contactItem: ContactItem, position: Int) {
        val contactList = mutableContactList.value!!.toMutableList()
        contactList.add(position, contactItem)
        mutableContactList.value = contactList
        updateContactIds()
    }
}