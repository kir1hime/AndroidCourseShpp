package com.example.androidcourseshpp.domain.usecase.contacts


import com.example.androidcourseshpp.domain.entity.sync.SyncStatus
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.first

interface DeleteContactsUseCase {
    suspend operator fun invoke(contactIds: List<Long>): Result<Unit, DataError.LocalError>
}

class DeleteContactsUseCaseImpl(
    private val contactsLocalRepository: ContactsLocalRepository
) : DeleteContactsUseCase {

    override suspend operator fun invoke(
        contactIds: List<Long>
    ): Result<Unit, DataError.LocalError> {
        val localContactsResult = contactsLocalRepository.getContacts().first()
        val errorResult = Result.Error(DataError.LocalError)

        if (localContactsResult is Result.Success) {
            val localContacts = localContactsResult.data
            val localContactsIds = localContacts.map { contact -> contact.contactInfo.id }
            if (!localContactsIds.containsAll(contactIds)) {
                return errorResult
            }
            val deletedContacts =
                localContacts.filter { contact -> contactIds.contains(contact.contactInfo.id) }

            val softDeletedContactsIds = mutableListOf<Long>()
            val hardDeletedContactsIds = mutableListOf<Long>()

            deletedContacts.forEach { contact ->
                val contactId = contact.contactInfo.id
                val contactSyncStatus = contact.syncStatus

                if (contactSyncStatus == SyncStatus.SYNCED) {
                    softDeletedContactsIds.add(contactId)
                }
                if (contactSyncStatus == SyncStatus.ADDED) {
                    hardDeletedContactsIds.add(contactId)
                }
            }

            val setSyncStatusResult =
                contactsLocalRepository.setSyncStatus(softDeletedContactsIds, SyncStatus.DELETED)
            if (setSyncStatusResult is Result.Error) {
                return errorResult
            }
            val deletingContactsResult =
                contactsLocalRepository.deleteContactsByIds(hardDeletedContactsIds)
            if (deletingContactsResult is Result.Error) {
                return errorResult
            }
            return Result.Success(Unit)
        }

        return errorResult
    }
}