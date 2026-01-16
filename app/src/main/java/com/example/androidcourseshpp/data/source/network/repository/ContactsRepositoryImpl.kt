package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.entity.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.entity.contacts.GetUserContactsModel
import com.example.androidcourseshpp.data.source.network.service.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val serviceProviderHolder: RetrofitServiceProviderHolder,
    private val userDataProvider: UserDataProvider
) : ContactsRepository {


    override suspend fun addContact(contact: ContactInfo) {
        withContext(Dispatchers.IO) {
            serviceProviderHolder.serviceProvider.getContactsService().addContact(
                ContactDataModel(userDataProvider.getUserServerId(), contact.id)
            )
        }
    }

    override suspend fun deleteContact(contact: ContactInfo) {
        withContext(Dispatchers.IO) {
            serviceProviderHolder.serviceProvider.getContactsService().deleteContact(
                ContactDataModel(userDataProvider.getUserServerId(), contact.id)
            )
        }
    }

    override suspend fun deleteContacts(contacts: List<ContactInfo>) {
        withContext(Dispatchers.IO) {
            contacts.map { contactItem ->
                async {
                    serviceProviderHolder.serviceProvider.getContactsService()
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
            response = serviceProviderHolder.serviceProvider.getContactsService()
                .getUserContacts(userDataProvider.getUserServerId())
        }

        val contactItemList = response.contacts.map { contact ->
            ContactInfo(
                id = contact.id,
                name = contact.name ?: "",
                career = contact.career ?: "",
                avatarURL = contact.image ?: ""
            )
        }
        return contactItemList
    }
}