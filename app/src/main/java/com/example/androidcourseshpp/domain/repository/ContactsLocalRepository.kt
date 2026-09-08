package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ContactsLocalRepository {
    suspend fun addContact(contact: ContactInfo): Result<Unit, DataError.LocalError>
    suspend fun addContacts(contacts: List<ContactInfo>): Result<Unit, DataError.LocalError>
    fun getContacts(): Flow<Result<List<SyncContactInfo>, DataError.LocalError>>
    suspend fun getContactById(id: Long): Result<SyncContactInfo?, DataError.LocalError>
    suspend fun deleteContactById(id: Long): Result<Unit, DataError.LocalError>
    suspend fun clearContacts(): Result<Unit, DataError.LocalError>
    suspend fun deleteContactsByIds(ids: List<Long>): Result<Unit, DataError.LocalError>
    fun isDatabaseSynced(): Boolean
    fun setDatabaseSynced(isSynced: Boolean)
    suspend fun setSyncStateToContact(
        contactId: Long,
        syncAction: SyncAction
    ): Result<Unit, DataError.LocalError>

    suspend fun refreshContacts(
        newContacts: List<ContactInfo>,
        deletedContactIds: List<Long>
    ): Result<Unit, DataError.LocalError>
}