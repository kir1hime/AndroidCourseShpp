package com.example.androidcourseshpp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.androidcourseshpp.data.local.database.dbentity.ContactDbEntity
import com.example.androidcourseshpp.data.local.database.utils.SyncState
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contactDbEntity: ContactDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContacts(contactDbEntities: List<ContactDbEntity>)

    @Query("SELECT * FROM contacts")
    fun getContacts(): Flow<List<ContactDbEntity>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getContactById(id: Long): ContactDbEntity?

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteContactById(id: Long)

    @Query("DELETE FROM contacts")
    suspend fun clearContacts()

    @Query("DELETE FROM contacts WHERE id IN (:ids)")
    suspend fun deleteContactsByIds(ids: List<Long>)

    @Query("UPDATE contacts SET sync_state = :syncState WHERE id IN (:ids)")
    suspend fun setSyncState(ids: List<Long>, syncState: SyncState)

    @Query("SELECT id FROM contacts WHERE sync_state != :synced")
    suspend fun getSyncedContactsIds(synced: SyncState = SyncState.SYNCED): List<Long>

    @Transaction
    suspend fun refreshContacts(newContacts: List<ContactDbEntity>, deletedContactIds: List<Long>) {
        val syncedContactIds = getSyncedContactsIds().toSet()
        deleteContactsByIds(deletedContactIds.filter { contactId -> contactId !in syncedContactIds })
        addContacts(newContacts.filter {contact -> contact.id !in syncedContactIds })
    }
}