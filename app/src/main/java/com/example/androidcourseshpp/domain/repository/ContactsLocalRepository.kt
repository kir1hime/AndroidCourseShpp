package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ContactsLocalRepository {
    suspend fun addContact(contact: ContactInfo): Result<Unit>
    suspend fun addContacts(contacts: List<ContactInfo>): Result<Unit>
    fun getContacts(): Flow<List<ContactInfo>>
    suspend fun getContactById(id: Int): Result<ContactInfo?>
    suspend fun deleteContactById(id: Int): Result<Unit>
    suspend fun clearContacts(): Result<Unit>
    suspend fun deleteContactsByIds(ids: List<Int>): Result<Unit>
}