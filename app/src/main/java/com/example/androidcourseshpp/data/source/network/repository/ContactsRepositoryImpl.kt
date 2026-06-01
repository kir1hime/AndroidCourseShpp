package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.model.contacts.GetUserContactsModel
import com.example.androidcourseshpp.data.source.network.service.contacts.ContactsService
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactsService: ContactsService,
    private val userDataProvider: UserDataProvider
) : ContactsRepository {


    override suspend fun addContact(contactId: Long) {
        withContext(Dispatchers.IO) {
            contactsService.addContact(
                ContactDataModel(userDataProvider.getUserServerId(), contactId)
            )
        }
    }

    override suspend fun deleteContact(contactId: Long) {
        withContext(Dispatchers.IO) {
            contactsService.deleteContact(
                ContactDataModel(userDataProvider.getUserServerId(), contactId)
            )
        }
    }

    override suspend fun deleteContacts(contacts: List<ContactInfo>) {
        withContext(Dispatchers.IO) {
            contacts.map { contactItem ->
                async {
                    contactsService.deleteContact(
                        ContactDataModel(userDataProvider.getUserServerId(), contactItem.id)
                    )
                }
            }.awaitAll()
        }
    }

    override suspend fun loadContacts(): List<ContactInfo> {
        val response: GetUserContactsModel

        withContext(Dispatchers.IO) {
            response = contactsService.getUserContacts(userDataProvider.getUserServerId())
        }

        val contactItemList = response.contacts.map { contact -> contact.toContactInfo() }
        return contactItemList
    }
}