package com.example.androidcourseshpp.data.network.service.contacts

import com.example.androidcourseshpp.data.network.model.contacts.ContactRequestModel
import com.example.androidcourseshpp.data.network.model.contacts.GetUserContactsResponseModel

interface ContactsService {

    suspend fun addContact(data: ContactRequestModel)
    suspend fun deleteContact(data: ContactRequestModel)
    suspend fun getUserContacts(userId: Long): GetUserContactsResponseModel
}