package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.mapper.toContactInfo
import com.example.androidcourseshpp.data.source.network.service.contacts.ContactsService
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


    override suspend fun addContact(contactId: Long): Result<Unit, DataError.Network> {
        val responseResult = contactsService.addContact(
            userId = userDataProvider.getUserServerId(), contactId = contactId
        )
        return responseResult
    }


    override suspend fun deleteContact(contactId: Long): Result<Unit, DataError.Network> {
        val responseResult = contactsService.deleteContact(
            userId = userDataProvider.getUserServerId(), contactId = contactId
        )
        return responseResult
    }


    override suspend fun deleteContacts(contactIds: List<Long>): Result<Unit, DataError.Network> =
        coroutineScope {
            val userId = userDataProvider.getUserServerId()

            val deferredResults = contactIds.map { contactId ->
                async {
                    contactsService.deleteContact(userId = userId, contactId = contactId)
                }
            }

            val results = deferredResults.awaitAll()
            val errorResult = results.find { result -> result is Result.Error }
            if (errorResult != null) {
                return@coroutineScope errorResult
            }
            Result.Success(Unit)
        }


    override suspend fun loadContacts(): Result<List<ContactInfo>, DataError.Network> {
        val responseResult =
            contactsService.getUserContacts(userId = userDataProvider.getUserServerId())

        return responseResult.mapResult { result ->
            result.contacts.map { contact ->
                contact.toContactInfo()
            }
        }
    }
}