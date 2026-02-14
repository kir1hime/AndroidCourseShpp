package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.data.source.network.utils.wrapNetworkExceptions
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val servicesProvider: ServicesProvider,
    private val userDataProvider: UserDataProvider
) : ContactsRepository {


    override suspend fun addContact(contactId: Int) = wrapNetworkExceptions {
        servicesProvider.getContactsService().addContact(
            ContactDataModel(userDataProvider.getUserServerId(), contactId)
        )
    }

    override suspend fun deleteContact(contactId: Int) = wrapNetworkExceptions {
        servicesProvider.getContactsService().deleteContact(
            ContactDataModel(userDataProvider.getUserServerId(), contactId)
        )
    }


    override suspend fun deleteContacts(contacts: List<ContactInfo>): Result<Unit> =
        wrapNetworkExceptions {
            coroutineScope {
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

    override suspend fun loadContacts(): Result<List<ContactInfo>> = wrapNetworkExceptions {
        val response = servicesProvider.getContactsService()
            .getUserContacts(userDataProvider.getUserServerId())

        val contactItemList = response.contacts.map { contact -> contact.toContactInfo() }
        contactItemList
    }
}