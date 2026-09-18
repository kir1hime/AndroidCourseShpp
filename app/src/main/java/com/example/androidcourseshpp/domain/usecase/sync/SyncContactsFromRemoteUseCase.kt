package com.example.androidcourseshpp.domain.usecase.sync

import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.entity.sync.SyncStatus
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.first


class SyncContactsFromRemoteUseCase(
    private val contactsLocalRepository: ContactsLocalRepository,
    private val contactsNetworkRepository: ContactsNetworkRepository
) {

    suspend operator fun invoke(): Result<Unit, DataError> {
        when (val remoteContactsResult = contactsNetworkRepository.getContacts()) {
            is Result.Error -> {
                return remoteContactsResult
            }

            is Result.Success -> {
                val remoteContacts = remoteContactsResult.data
                val remoteContactsIds = remoteContacts.map { contact -> contact.id }

                when (val localContactsResult = contactsLocalRepository.getContacts().first()) {
                    is Result.Error<*> -> {
                        return localContactsResult
                    }

                    is Result.Success -> {
                        val localContacts = localContactsResult.data
                        val localContactsIds =
                            localContacts.map { contact -> contact.contactInfo.id }

                        val addedContacts =
                            remoteContacts.filter { contact -> !localContactsIds.contains(contact.id) }
                                .map { contact -> SyncContactInfo(contact, SyncStatus.SYNCED) }

                        val deletedContactsIds = localContactsIds.filter { contactId ->
                            !remoteContactsIds.contains(contactId)
                        }

                        return contactsLocalRepository.refreshContacts(
                            newContacts = addedContacts,
                            deletedContactsIds = deletedContactsIds,
                        )
                    }
                }
            }
        }
    }
}