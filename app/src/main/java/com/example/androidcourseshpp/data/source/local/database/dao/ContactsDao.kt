package com.example.androidcourseshpp.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.androidcourseshpp.data.source.local.database.dbentity.ContactDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Insert
    suspend fun addContact(contactDbEntity: ContactDbEntity)

    @Insert
    suspend fun addContacts(contactDbEntities: List<ContactDbEntity>)

    @Query("SELECT * FROM contacts")
    fun getContacts(): Flow<List<ContactDbEntity>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getContactById(id: Int): ContactDbEntity?

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteContactById(id: Int)

    @Query("DELETE FROM contacts")
    suspend fun clearContacts()

    @Query("DELETE FROM contacts WHERE id IN (:ids)")
    suspend fun deleteContactsByIds(ids: List<Int>)
}