package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo

interface ContactsRepository {
    suspend fun addContact(contactId: Int)
    suspend fun deleteContact(contactId: Int)
    suspend fun deleteContacts(contacts: List<ContactInfo>)
    suspend fun loadContacts(): List<ContactInfo>
}