package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.contacts.ContactRequestModel
import com.example.androidcourseshpp.data.source.network.service.contacts.ContactsService
import com.example.androidcourseshpp.data.source.network.utils.wrapNetworkExceptions
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject

class ContactsNetworkRepositoryImpl @Inject constructor(
    private val contactsService: ContactsService,
    private val userDataProvider: UserDataProvider
) : ContactsNetworkRepository {


    override suspend fun addContact(contactId: Long) = wrapNetworkExceptions {
        contactsService.addContact(
            ContactRequestModel(userDataProvider.getUserServerId(), contactId)
        )
    }

    override suspend fun deleteContact(contactId: Long) = wrapNetworkExceptions {
        contactsService.deleteContact(
            ContactRequestModel(userDataProvider.getUserServerId(), contactId)
        )
    }


    override suspend fun deleteContacts(contactIds: List<Long>): Result<Unit> =
        wrapNetworkExceptions {
            contactIds.forEach { id ->
                contactsService.deleteContact(
                    ContactRequestModel(
                        userDataProvider.getUserServerId(),
                        id
                    )
                )
            }
        }

    override suspend fun loadContacts(): Result<List<ContactInfo>> = wrapNetworkExceptions {
        val response = contactsService.getUserContacts(userDataProvider.getUserServerId())

        val contactItemList = response.contacts.map { contact -> contact.toContactInfo() }
        contactItemList
    }
}