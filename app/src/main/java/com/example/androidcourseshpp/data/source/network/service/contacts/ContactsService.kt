package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.source.network.entity.contacts.GetUserContactsEntity

interface ContactsService {

    suspend fun addContact(contactData: ContactData)
    suspend fun deleteContact(contactData: ContactData)
    suspend fun getUserContacts(userId:Int): GetUserContactsEntity
}