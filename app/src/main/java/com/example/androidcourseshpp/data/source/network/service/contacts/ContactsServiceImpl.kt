package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.RetrofitConfig
import com.example.androidcourseshpp.data.source.network.api.contacts.ContactsAPI
import com.example.androidcourseshpp.data.source.network.dto.contacts.AddContactRequestDTO
import com.example.androidcourseshpp.data.source.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.source.network.service.BaseRetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsServiceImpl @Inject constructor(config: RetrofitConfig) :
    BaseRetrofitService(config),
    ContactsService {

    private val contactsApi = retrofit.create(ContactsAPI::class.java)

    override suspend fun addContact(contactData: ContactData) {
        processRetrofitExceptions {
            contactsApi.addContact(contactData.userId, AddContactRequestDTO(contactData.contactId))
        }
    }

    override suspend fun deleteContact(contactData: ContactData) {
        processRetrofitExceptions {
            contactsApi.deleteContact(contactData.userId, contactData.contactId)
        }
    }

    override suspend fun getUserContacts(userId: Int) = processRetrofitExceptions {
        contactsApi.getUserContacts(userId).data
    }
}
