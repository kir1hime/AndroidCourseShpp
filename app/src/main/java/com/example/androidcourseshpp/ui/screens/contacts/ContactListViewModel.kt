package com.example.androidcourseshpp.ui.screens.contacts


import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.contactlistdata.ContactListGenerator
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

class ContactListViewModel(
    private val contentResolver: ContentResolver,
    private val isAccessToContactsAllowed: Boolean
) : ViewModel() {
    val contactList: LiveData<MutableList<ContactItem>> get() = mutableContactList
    private val mutableContactList = MutableLiveData<MutableList<ContactItem>>()

    init {
        mutableContactList.value =
            ContactListGenerator(contentResolver, isAccessToContactsAllowed).getContactItems()
    }

    fun deleteContactItem(contactItem: ContactItem) {
        val indexToDelete = mutableContactList.value!!.indexOfFirst { it.name == contactItem.name }
        if (indexToDelete != -1) {
            mutableContactList.value!!.removeAt(indexToDelete)
        }
        updateContactIds()
    }

    fun deleteContactItem(position: Int) {
        mutableContactList.value!!.removeAt(position)

    }

    private fun updateContactIds() {
        for (i in mutableContactList.value!!.indices) {
            mutableContactList.value!![i].id = i
        }
    }

    fun addContactItem(contactItem: ContactItem, position: Int) {
        mutableContactList.value!!.add(position, contactItem)
        updateContactIds()
    }
}