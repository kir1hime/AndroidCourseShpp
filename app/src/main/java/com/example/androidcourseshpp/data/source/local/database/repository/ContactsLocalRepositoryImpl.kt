package com.example.androidcourseshpp.data.source.local.database.repository

import com.example.androidcourseshpp.data.source.local.database.dao.ContactsDao
import com.example.androidcourseshpp.data.source.local.database.dbentity.ContactDbEntity
import com.example.androidcourseshpp.data.source.local.database.utils.toSyncState
import com.example.androidcourseshpp.data.source.local.database.utils.wrapSQLiteException
import com.example.androidcourseshpp.data.source.local.userdata.DatabaseSyncProvider
import com.example.androidcourseshpp.data.source.network.utils.wrapNetworkExceptions
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactsLocalRepositoryImpl @Inject constructor(
    private val contactsDao: ContactsDao,
    private val databaseSyncProvider: DatabaseSyncProvider
) : ContactsLocalRepository {


    override suspend fun addContact(contact: ContactInfo) = wrapSQLiteException {
        contactsDao.addContact(ContactDbEntity.fromContactInfo(contact))
    }

    override suspend fun addContacts(contacts: List<ContactInfo>) = wrapSQLiteException {
        contactsDao.addContacts(
            contacts.map { contact ->
                ContactDbEntity.fromContactInfo(contact)
            }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getContacts(): Flow<Result<List<SyncContactInfo>>> =
        contactsDao.getContacts()
            .map { list -> Result.Success(list.map { it.toSyncContactInfo() }) }
            .catch { Result.Error(AppError.LocalStorageError) }


    override suspend fun getContactById(id: Int): Result<SyncContactInfo?> = wrapSQLiteException {
        return@wrapSQLiteException contactsDao.getContactById(id)?.toSyncContactInfo()
    }

    override suspend fun deleteContactById(id: Int) = wrapSQLiteException {
        contactsDao.deleteContactById(id)
    }

    override suspend fun clearContacts() = wrapSQLiteException {
        contactsDao.clearContacts()
    }

    override suspend fun deleteContactsByIds(ids: List<Int>) = wrapSQLiteException {
        contactsDao.deleteContactsByIds(ids)
    }

    override fun isDatabaseSynced() = databaseSyncProvider.isDatabaseSynced()

    override fun setDatabaseSynced(isSynced: Boolean) {
        databaseSyncProvider.markDatabaseAsSynced(isSynced)
    }


    override suspend fun setSyncStateToContact(contactId: Int, syncAction: SyncAction) =
        wrapNetworkExceptions {
            contactsDao.setContactSync(id = contactId, syncState = syncAction.toSyncState())
        }
}