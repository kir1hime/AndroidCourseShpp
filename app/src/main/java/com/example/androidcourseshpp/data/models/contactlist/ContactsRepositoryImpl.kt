package com.example.androidcourseshpp.data.models.contactlist

import com.example.androidcourseshpp.data.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.network.service.contacts.ContactsService
import com.example.androidcourseshpp.data.userdata.UserDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ContactsRepositoryImpl @Inject constructor(
    private val contactsService: ContactsService,
    private val userDataProvider: UserDataProvider
) : ContactsRepository {


    override suspend fun addContactItem(contactItem: ContactItem) {
        withContext(Dispatchers.IO) {
           contactsService.addContact(
                ContactData(userDataProvider.getUserServerId(), contactItem.id)
            )
        }
    }

    override suspend fun deleteContactItem(contactItem: ContactItem) {
        withContext(Dispatchers.IO) {
            contactsService.deleteContact(
                ContactData(userDataProvider.getUserServerId(), contactItem.id)
            )
        }
    }

    override suspend fun deleteContactItems(contactItems: List<ContactItem>) {
        withContext(Dispatchers.IO) {
            contactItems.map { contactItem ->
                async {
                   contactsService.deleteContact(
                            ContactData(userDataProvider.getUserServerId(), contactItem.id)
                        )
                }
            }.awaitAll()
        }
    }

    override suspend fun loadContacts(): List<ContactItem> {
        val response = contactsService.getUserContacts(userDataProvider.getUserServerId())

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