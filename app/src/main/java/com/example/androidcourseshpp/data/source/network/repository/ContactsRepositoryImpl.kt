package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.model.contacts.GetUserContactsModel
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val servicesProvider: ServicesProvider,
    private val userDataProvider: UserDataProvider
) : ContactsRepository {


    override suspend fun addContact(contact: ContactInfo) {
        withContext(Dispatchers.IO) {
            servicesProvider.getContactsService().addContact(
                ContactDataModel(userDataProvider.getUserServerId(), contact.id)
            )
        }
    }

    override suspend fun deleteContact(contact: ContactInfo) {
        withContext(Dispatchers.IO) {
            servicesProvider.getContactsService().deleteContact(
                ContactDataModel(userDataProvider.getUserServerId(), contact.id)
            )
        }
    }

    override suspend fun deleteContacts(contacts: List<ContactInfo>) {
        withContext(Dispatchers.IO) {
            contacts.map { contactItem ->
                async {
                    servicesProvider.getContactsService()
                        .deleteContact(
                            ContactDataModel(userDataProvider.getUserServerId(), contactItem.id)
                        )
                }
            }.awaitAll()
        }
    }

    override suspend fun loadContacts(): List<ContactInfo> {
        val response: GetUserContactsModel

        withContext(Dispatchers.IO) {
            response = servicesProvider.getContactsService()
                .getUserContacts(userDataProvider.getUserServerId())
        }

        val contactItemList = response.contacts.map { contact -> contact.toContactInfo() }
        return contactItemList
    }
}