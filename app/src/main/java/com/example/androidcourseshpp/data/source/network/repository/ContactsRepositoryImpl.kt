package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.source.network.service.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.entity.ContactItem
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


    override suspend fun addContactItem(contactItem: ContactItem) {
        withContext(Dispatchers.IO) {
            serviceProviderHolder.serviceProvider.getContactsService().addContact(
                ContactData(userDataProvider.getUserServerId(), contactItem.id)
            )
        }
    }

    override suspend fun deleteContactItem(contactItem: ContactItem) {
        withContext(Dispatchers.IO) {
            serviceProviderHolder.serviceProvider.getContactsService().deleteContact(
                ContactData(userDataProvider.getUserServerId(), contactItem.id)
            )
        }
    }

    override suspend fun deleteContactItems(contactItems: List<ContactItem>) {
        withContext(Dispatchers.IO) {
            contactItems.map { contactItem ->
                async {
                    serviceProviderHolder.serviceProvider.getContactsService()
                        .deleteContact(
                            ContactData(userDataProvider.getUserServerId(), contactItem.id)
                        )
                }
            }.awaitAll()
        }
    }

    override suspend fun loadContacts(): List<ContactItem> {
        val response = serviceProviderHolder.serviceProvider.getContactsService()
            .getUserContacts(userDataProvider.getUserServerId())

        val contactItemList = response.contacts.map { contact ->
            ContactItem(
                id = contact.id,
                name = contact.name ?: "",
                career = contact.career ?: "",
                avatarURL = contact.image ?: ""
            )
        }
        return contactItemList
    }
}