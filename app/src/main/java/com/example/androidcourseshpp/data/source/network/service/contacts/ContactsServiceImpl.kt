package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.api.contacts.ContactsAPI
import com.example.androidcourseshpp.data.source.network.dto.contacts.AddContactRequestDTO
import com.example.androidcourseshpp.data.source.network.model.contacts.ContactRequestModel
import com.example.androidcourseshpp.data.source.network.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsServiceImpl @Inject constructor(
    private val contactsApi: ContactsAPI
) : ContactsService {

    override suspend fun addContact(data: ContactRequestModel) {
        safeApiCall {
            contactsApi.addContact(data.userId, AddContactRequestDTO(data.contactId))
        }
    }

    override suspend fun deleteContact(data: ContactRequestModel) {
        safeApiCall {
            contactsApi.deleteContact(data.userId, data.contactId)
        }
    }

    override suspend fun getUserContacts(userId: Long) = safeApiCall {
        contactsApi.getUserContacts(userId).data
    }
}
