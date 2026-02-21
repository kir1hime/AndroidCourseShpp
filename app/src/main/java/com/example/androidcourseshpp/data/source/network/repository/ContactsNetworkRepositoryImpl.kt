package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.data.source.network.utils.wrapNetworkExceptions
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject

class ContactsNetworkRepositoryImpl @Inject constructor(
    private val servicesProvider: ServicesProvider,
    private val userDataProvider: UserDataProvider
) : ContactsNetworkRepository {


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


    override suspend fun deleteContacts(contactIds: List<Int>): Result<Unit> =
        wrapNetworkExceptions {
            contactIds.forEach { id ->
                servicesProvider.getContactsService()
                    .deleteContact(
                        ContactDataModel(userDataProvider.getUserServerId(), id)
                    )
            }
        }

    override suspend fun loadContacts(): Result<List<ContactInfo>> = wrapNetworkExceptions {
        val response = servicesProvider.getContactsService()
            .getUserContacts(userDataProvider.getUserServerId())

        val contactItemList = response.contacts.map { contact -> contact.toContactInfo() }
        contactItemList
    }
}