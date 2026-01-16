package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo

interface ContactsRepository {

    suspend fun addContact(contact: ContactInfo)

    suspend fun deleteContact(contact: ContactInfo)

    suspend fun deleteContacts(contacts: List<ContactInfo>)
    suspend fun loadContacts(): List<ContactInfo>
}