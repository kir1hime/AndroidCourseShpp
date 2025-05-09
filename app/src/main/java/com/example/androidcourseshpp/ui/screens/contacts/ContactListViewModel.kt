package com.example.androidcourseshpp.ui.screens.contacts


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ContactItem

class ContactListViewModel : ViewModel() {
    val contactList : LiveData<List<ContactItem>> get() = mutableContactList
    private val mutableContactList = MutableLiveData<List<ContactItem>>()

    init {
        mutableContactList.value = ContactListGenerator().getContactItems()
    }
}