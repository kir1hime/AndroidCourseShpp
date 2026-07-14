package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ContactsLocalRepository {
    suspend fun addContact(contact: ContactInfo): Result<Unit>
    suspend fun addContacts(contacts: List<ContactInfo>): Result<Unit>
    fun getContacts(): Flow<Result<List<SyncContactInfo>>>
    suspend fun getContactById(id: Long): Result<SyncContactInfo?>
    suspend fun deleteContactById(id: Long): Result<Unit>
    suspend fun clearContacts(): Result<Unit>
    suspend fun deleteContactsByIds(ids: List<Long>): Result<Unit>
    fun isDatabaseSynced(): Boolean
    fun setDatabaseSynced(isSynced: Boolean)
    suspend fun setSyncStateToContact(contactId: Long, syncAction: SyncAction): Result<Unit>
    suspend fun refreshContacts(
        newContacts: List<ContactInfo>,
        deletedContactIds: List<Long>
    ): Result<Unit>
}