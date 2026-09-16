package com.example.androidcourseshpp.data.network.service.contacts

import com.example.androidcourseshpp.data.network.api.contacts.ContactsAPI
import com.example.androidcourseshpp.data.network.dto.contacts.AddContactRequestDTO
import com.example.androidcourseshpp.data.network.model.contacts.ContactRequestModel
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsServiceImpl @Inject constructor(private val contactsApi: ContactsAPI) :
    BaseRetrofitService(),
    ContactsService {

    override suspend fun addContact(data: ContactRequestModel) {
        processRetrofitExceptions {
            contactsApi.addContact(data.userId, AddContactRequestDTO(data.contactId))
        }
    }

    override suspend fun deleteContact(data: ContactRequestModel) {
        processRetrofitExceptions {
            contactsApi.deleteContact(data.userId, data.contactId)
        }
    }

    override suspend fun getUserContacts(userId: Long) = processRetrofitExceptions {
        contactsApi.getUserContacts(userId).data
    }
}
