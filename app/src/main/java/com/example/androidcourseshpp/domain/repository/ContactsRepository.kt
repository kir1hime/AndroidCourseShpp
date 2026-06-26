package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo

interface ContactsRepository {
    suspend fun addContact(contactId: Long)
    suspend fun deleteContact(contactId: Long)
    suspend fun deleteContacts(contacts: List<ContactInfo>)
    suspend fun loadContacts(): List<ContactInfo>
}