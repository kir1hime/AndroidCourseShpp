package com.example.androidcourseshpp.domain.usecase.sync

import com.example.androidcourseshpp.domain.entity.sync.SyncStatus
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.first

class SyncContactsToRemoteUseCase(
    private val contactsNetworkRepository: ContactsNetworkRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {

    suspend operator fun invoke(): Result<Unit, DataError> {
        when (val localContactsResult = contactsLocalRepository.getContacts().first()) {
            is Result.Error -> {
                return localContactsResult
            }

            is Result.Success -> {
                val localContacts = localContactsResult.data

                val addedContactsIds =
                    localContacts.filter { contact -> contact.syncStatus == SyncStatus.ADDED }
                        .map { it.contactInfo.id }
                val deletedContactsIds =
                    localContacts.filter { contact -> contact.syncStatus == SyncStatus.DELETED }
                        .map { it.contactInfo.id }

                if (addedContactsIds.isNotEmpty()) {
                    val addingContactsResult =
                        contactsNetworkRepository.addContacts(addedContactsIds)
                    if (addingContactsResult is Result.Error) return addingContactsResult

                    val markingContactsAsSyncedResult =
                        contactsLocalRepository.setSyncStatus(addedContactsIds, SyncStatus.SYNCED)
                    if (markingContactsAsSyncedResult is Result.Error) return markingContactsAsSyncedResult
                }

                if (deletedContactsIds.isNotEmpty()) {
                    val deletingContactsFromRemoteResult =
                        contactsNetworkRepository.deleteContacts(deletedContactsIds)
                    if (deletingContactsFromRemoteResult is Result.Error) return deletingContactsFromRemoteResult

                    val deletingContactsFromLocalResult =
                        contactsLocalRepository.deleteContactsByIds(deletedContactsIds)
                    if (deletingContactsFromLocalResult is Result.Error) return deletingContactsFromLocalResult
                }
                return Result.Success(Unit)
            }
        }
    }
}

