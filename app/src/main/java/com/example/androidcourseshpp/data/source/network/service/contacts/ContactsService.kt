package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.entity.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.entity.contacts.GetUserContactsModel

interface ContactsService {

    suspend fun addContact(contactData: ContactDataModel)
    suspend fun deleteContact(contactData: ContactDataModel)
    suspend fun getUserContacts(userId:Int): GetUserContactsModel
}