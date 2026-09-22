package com.example.androidcourseshpp.data.network.repository

import com.example.androidcourseshpp.data.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.network.mapper.toContactInfo
import com.example.androidcourseshpp.data.network.service.contacts.ContactsService
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.mapResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ContactsNetworkRepositoryImpl @Inject constructor(
    private val contactsService: ContactsService,
    private val userDataProvider: UserDataProvider
) : ContactsNetworkRepository {


    private suspend fun addContact(contactId: Long): Result<Unit, DataError.NetworkError> {
        val responseResult = contactsService.addContact(
            userId = userDataProvider.getUserServerId(), contactId = contactId
        )
        return responseResult
    }

    override suspend fun addContacts(contactsIds: List<Long>): Result<Unit, DataError.NetworkError> =
        coroutineScope {
            val deferredAddingResults = contactsIds.map { contact ->
                async {
                    addContact(contact)
                }
            }
            val results = deferredAddingResults.awaitAll()
            val errorResult = results.find { result -> result is Result.Error }
            if (errorResult != null) {
                return@coroutineScope errorResult
            }
            Result.Success(Unit)
        }


    private suspend fun deleteContact(contactId: Long): Result<Unit, DataError.NetworkError> {
        val responseResult = contactsService.deleteContact(
            userId = userDataProvider.getUserServerId(), contactId = contactId
        )
        return responseResult
    }


    override suspend fun deleteContacts(contactsIds: List<Long>): Result<Unit, DataError.NetworkError> =
        coroutineScope {
            val deferredDeletingResults = contactsIds.map { contactId ->
                async {
                    deleteContact(contactId)
                }
            }
            val results = deferredDeletingResults.awaitAll()
            val errorResult = results.find { result -> result is Result.Error }
            if (errorResult != null) {
                return@coroutineScope errorResult
            }
            Result.Success(Unit)
        }


    override suspend fun getContacts(): Result<List<ContactInfo>, DataError.NetworkError> {
        val responseResult =
            contactsService.getUserContacts(userId = userDataProvider.getUserServerId())

        return responseResult.mapResult { result ->
            result.contacts.map { contact ->
                contact.toContactInfo()
            }
        }
    }
}