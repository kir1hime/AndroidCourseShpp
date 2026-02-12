package com.example.androidcourseshpp.data.source.local.database.repository

import com.example.androidcourseshpp.data.source.local.database.dao.ContactsDao
import com.example.androidcourseshpp.data.source.local.database.dbentity.ContactDbEntity
import com.example.androidcourseshpp.data.source.local.database.utils.wrapSQLiteException
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactsLocalRepositoryImpl(
    private val contactsDao: ContactsDao
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

    override fun getContacts(): Flow<List<ContactInfo>> {
        return contactsDao.getContacts()
            .map { contactDbEntities ->
                contactDbEntities.map { dbEntity ->
                    dbEntity.toContactInfo()
                }
            }
    }

    override suspend fun getContactById(id: Int): ContactInfo? = wrapSQLiteException {
        return@wrapSQLiteException contactsDao.getContactById(id)?.toContactInfo()
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
}