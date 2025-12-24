package com.example.androidcourseshpp.data.network.service.contacts

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.api.contacts.ContactsAPI
import com.example.androidcourseshpp.data.network.dto.contacts.AddContactRequestDTO
import com.example.androidcourseshpp.data.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsServiceImpl @Inject constructor(val config: RetrofitConfig) : BaseRetrofitService(config),
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

    override suspend fun getUserContacts(userId: Long) = processRetrofitExceptions {
        contactsApi.getUserContacts(userId).data
    }
}
