package com.example.androidcourseshpp.data.models.contactlist

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ContactsRepository {

    val contactList: StateFlow<List<ContactItem>>
    suspend fun initContactList()
    suspend fun addContactItem(contactItem: ContactItem)

    suspend fun deleteContactItem(contactItem: ContactItem)

    suspend fun deleteContactItems(contactItems: List<ContactItem>)
}