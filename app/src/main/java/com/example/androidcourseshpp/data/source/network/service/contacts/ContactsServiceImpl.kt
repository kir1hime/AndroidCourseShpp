package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.api.contacts.ContactsAPI
import com.example.androidcourseshpp.data.source.network.dto.contacts.AddContactRequestDTO
import com.example.androidcourseshpp.data.source.network.mapper.toGetUserContactsResponseModel
import com.example.androidcourseshpp.data.source.network.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsServiceImpl @Inject constructor(
    private val contactsApi: ContactsAPI
) : ContactsService {

    override suspend fun addContact(userId: Long, contactId: Long) = safeApiCall {
        contactsApi.addContact(userId, AddContactRequestDTO(contactId))
    }

    override suspend fun deleteContact(userId: Long, contactId: Long) = safeApiCall {
        contactsApi.deleteContact(userId, contactId)
    }

    override suspend fun getUserContacts(userId: Long) = safeApiCall {
        val response = contactsApi.getUserContacts(userId)
        response.toGetUserContactsResponseModel()
    }
}
