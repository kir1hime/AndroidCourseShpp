package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ContactsLocalRepository {
    suspend fun addContact(contact: ContactInfo): Result<Unit>
    suspend fun addContacts(contacts: List<ContactInfo>): Result<Unit>
    fun getContacts(): Flow<Result<List<SyncContactInfo>>>
    suspend fun getContactById(id: Int): Result<ContactInfo?>
    suspend fun deleteContactById(id: Int): Result<Unit>
    suspend fun clearContacts(): Result<Unit>
    suspend fun deleteContactsByIds(ids: List<Int>): Result<Unit>

    fun isDatabaseSynced(): Boolean
    fun setDatabaseSynced(isSynced: Boolean)

    suspend fun markContactAsDeleted(contactId: Int): Result<Unit>
    suspend fun markContactAsAdded(contactId: Int): Result<Unit>
}