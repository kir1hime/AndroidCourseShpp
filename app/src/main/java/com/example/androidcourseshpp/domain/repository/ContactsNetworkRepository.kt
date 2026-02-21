package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.utils.Result

interface ContactsNetworkRepository {
    suspend fun addContact(contactId: Int): Result<Unit>
    suspend fun deleteContact(contactId: Int): Result<Unit>
    suspend fun deleteContacts(contactIds: List<Int>): Result<Unit>

    suspend fun loadContacts(): Result<List<ContactInfo>>
}