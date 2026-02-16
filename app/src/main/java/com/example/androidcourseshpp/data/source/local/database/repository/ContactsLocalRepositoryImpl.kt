package com.example.androidcourseshpp.data.source.local.database.repository

import com.example.androidcourseshpp.data.source.local.database.dao.ContactsDao
import com.example.androidcourseshpp.data.source.local.database.dbentity.ContactDbEntity
import com.example.androidcourseshpp.data.source.local.database.utils.wrapSQLiteException
import com.example.androidcourseshpp.data.source.local.userdata.DatabaseValidityProvider
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ContactsLocalRepositoryImpl @Inject constructor(
    private val contactsDao: ContactsDao,
    private val databaseValidityProvider: DatabaseValidityProvider
) : ContactsLocalRepository {

    private val contactListRefreshingTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

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
    override fun getContacts(): Flow<Result<List<ContactInfo>>> =
        contactListRefreshingTrigger.onStart { emit(Unit) }
            .flatMapLatest {
                contactsDao.getContacts()
                    .map { list -> Result.Success(list.map { it.toContactInfo() }) }
                    .catch { Result.Error(AppError.LocalStorageError) }
            }


    override suspend fun getContactById(id: Int): Result<ContactInfo?> = wrapSQLiteException {
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

    override fun isDataValid() = databaseValidityProvider.isDataValid()

    override fun setDataValidity(isDataValid: Boolean) {
        databaseValidityProvider.setDataValidity(isDataValid())
    }

    override suspend fun refreshContactList() {
        contactListRefreshingTrigger.emit(Unit)
    }
}