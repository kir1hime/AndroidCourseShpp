package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.utils.Result

interface ContactsNetworkRepository {
    suspend fun addContact(contactId: Long): Result<Unit>
    suspend fun deleteContact(contactId: Long): Result<Unit>
    suspend fun deleteContacts(contactIds: List<Long>): Result<Unit>

    suspend fun loadContacts(): Result<List<ContactInfo>>
}