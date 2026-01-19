package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.model.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.model.contacts.GetUserContactsModel

interface ContactsService {

    suspend fun addContact(contactData: ContactDataModel)
    suspend fun deleteContact(contactData: ContactDataModel)
    suspend fun getUserContacts(userId:Int): GetUserContactsModel
}