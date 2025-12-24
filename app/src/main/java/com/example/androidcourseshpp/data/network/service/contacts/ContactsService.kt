package com.example.androidcourseshpp.data.network.service.contacts

import com.example.androidcourseshpp.data.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.network.entity.contacts.GetUserContactsEntity

interface ContactsService {

    suspend fun addContact(contactData: ContactData)
    suspend fun deleteContact(contactData: ContactData)
    suspend fun getUserContacts(userId:Long): GetUserContactsEntity
}