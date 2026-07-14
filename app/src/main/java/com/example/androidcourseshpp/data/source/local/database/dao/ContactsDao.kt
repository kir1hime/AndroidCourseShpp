package com.example.androidcourseshpp.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.androidcourseshpp.data.source.local.database.dbentity.ContactDbEntity
import com.example.androidcourseshpp.data.source.local.database.utils.SyncState
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Insert
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

    @Query("UPDATE contacts SET sync_state = :syncState WHERE id = :id")
    suspend fun setContactSync(id: Long, syncState: SyncState)

    @Transaction
    suspend fun refreshContacts(
        newContacts: List<ContactDbEntity>,
        deletedContactIds: List<Long>
    ) {
        addContacts(newContacts)
        deleteContactsByIds(deletedContactIds)

        deletedContactIds.forEach { contactId ->
            setContactSync(contactId, SyncState.SYNCED)
        }
        newContacts.forEach { contact ->
            setContactSync(contact.id, SyncState.SYNCED)
        }
    }

}