package com.example.androidcourseshpp.domain.usecase.sync

import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onError
import com.example.androidcourseshpp.domain.utils.onSuccess
import kotlinx.coroutines.flow.first

class SyncContactsToRemoteUseCase(
    private val contactsNetworkRepository: ContactsNetworkRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(): Result<Unit, DataError> {
        val result = contactsLocalRepository.getContacts().first()

        if (result is Result.Success) {
            val contacts = result.data
            var isAllContactsSynced = true

            contacts.filter { it.syncState != SyncAction.SYNCED }.forEach { contact ->
                val contactId = contact.contactInfo.id

                when (contact.syncState) {
                    SyncAction.ADDED -> {
                        contactsNetworkRepository.addContact(contactId).onSuccess {
                            contactsLocalRepository.setSyncStateToContact(
                                contactId,
                                SyncAction.SYNCED
                            )
                        }.onError { isAllContactsSynced = false }
                    }

                    SyncAction.DELETED -> {
                        contactsNetworkRepository.deleteContact(contactId).onSuccess {
                            contactsLocalRepository.deleteContactById(contactId)
                        }.onError { isAllContactsSynced = false }
                    }

                    else -> return@forEach
                }
            }
            return if (isAllContactsSynced) Result.Success(Unit) else Result.Error(DataError.NetworkError.SERVER_ERROR)
        } else {
            return Result.Error(DataError.LocalError)
        }
    }
}