package com.example.androidcourseshpp.data.source.local.database.repository

import com.example.androidcourseshpp.data.source.local.database.dao.ContactsDao
import com.example.androidcourseshpp.data.source.local.database.dbentity.toContactDBEntity
import com.example.androidcourseshpp.data.source.local.database.utils.safeDBQuery
import com.example.androidcourseshpp.data.source.local.database.utils.toSyncState
import com.example.androidcourseshpp.data.source.local.userdata.DatabaseSyncProvider
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactsLocalRepositoryImpl @Inject constructor(
    private val contactsDao: ContactsDao,
    private val databaseSyncProvider: DatabaseSyncProvider
) : ContactsLocalRepository {

    override suspend fun addContact(contact: ContactInfo) = safeDBQuery {
        contactsDao.addContact(contactDbEntity = contact.toContactDBEntity())
    }

    override suspend fun addContacts(contacts: List<ContactInfo>) = safeDBQuery {
        contactsDao.addContacts(
            contactDbEntities = contacts.map { contact ->
                contact.toContactDBEntity()
            }
        )
    }

    override fun getContacts(): Flow<Result<List<SyncContactInfo>, DataError.LocalError>> =
        contactsDao.getContacts().map { list ->
            val result: Result<List<SyncContactInfo>, DataError.LocalError> =
                Result.Success(list.map { it.toSyncContactInfo() })

            result
        }.catch { emit(Result.Error(DataError.LocalError)) }


    override suspend fun getContactById(id: Long) = safeDBQuery {
        return@safeDBQuery contactsDao.getContactById(id)?.toSyncContactInfo()
    }

    override suspend fun deleteContactById(id: Long) = safeDBQuery {
        contactsDao.deleteContactById(id)
    }

    override suspend fun clearContacts() = safeDBQuery {
        contactsDao.clearContacts()
    }

    override suspend fun deleteContactsByIds(ids: List<Long>) = safeDBQuery {
        contactsDao.deleteContactsByIds(ids)
    }

    override suspend fun setSyncStateToContact(contactId: Long, syncAction: SyncAction) =
        safeDBQuery {
            contactsDao.setContactSync(id = contactId, syncState = syncAction.toSyncState())
        }

    override suspend fun refreshContacts(
        newContacts: List<ContactInfo>,
        deletedContactIds: List<Long>
    ) = safeDBQuery {
        val newDBEntities = newContacts.map { contact -> contact.toContactDBEntity() }

        contactsDao.refreshContacts(
            newContacts = newDBEntities,
            deletedContactIds = deletedContactIds
        )
    }

    override fun isDatabaseSynced() = databaseSyncProvider.isDatabaseSynced()

    override fun setDatabaseSynced(isSynced: Boolean) {
        databaseSyncProvider.markDatabaseAsSynced(isSynced)
    }
}