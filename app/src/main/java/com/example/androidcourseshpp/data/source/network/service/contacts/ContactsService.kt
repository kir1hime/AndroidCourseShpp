package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.model.contacts.GetUserContactsResponseModel

interface ContactsService {

    suspend fun addContact(userId: Long, contactId: Long)
    suspend fun deleteContact(userId: Long, contactId: Long)
    suspend fun getUserContacts(userId: Long): GetUserContactsResponseModel
}