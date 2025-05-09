package com.example.androidcourseshpp.ui.screens.contacts


import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.contactlistdata.ContactListGenerator
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

class ContactListViewModel(
    private val contentResolver: ContentResolver,
    private val isAccessToContactsAllowed : () -> Boolean
) : ViewModel() {
    val contactList: LiveData<List<ContactItem>> get() = mutableContactList
    private val mutableContactList = MutableLiveData<List<ContactItem>>()

    init {
        mutableContactList.value =
            ContactListGenerator(contentResolver, isAccessToContactsAllowed).getContactItems()
    }
}