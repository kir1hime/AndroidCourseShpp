package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.utils.Result

interface ContactsRepository {
    suspend fun addContact(contactId: Int): Result<Unit>
    suspend fun deleteContact(contactId: Int): Result<Unit>
    suspend fun deleteContacts(contacts: List<ContactInfo>): Result<Unit>

    suspend fun loadContacts(): Result<List<ContactInfo>>
}