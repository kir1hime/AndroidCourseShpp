package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface ContactsNetworkRepository {
    suspend fun addContact(contactId: Long): Result<Unit, DataError.Network>
    suspend fun deleteContact(contactId: Long): Result<Unit, DataError.Network>
    suspend fun deleteContacts(contactIds: List<Long>): Result<Unit, DataError.Network>

    suspend fun loadContacts(): Result<List<ContactInfo>, DataError.Network>
}