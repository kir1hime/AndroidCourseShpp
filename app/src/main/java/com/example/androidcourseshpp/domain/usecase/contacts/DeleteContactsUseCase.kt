package com.example.androidcourseshpp.domain.usecase.contacts


import com.example.androidcourseshpp.domain.entity.sync.SyncStatus
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.first

class DeleteContactsUseCase(private val contactsLocalRepository: ContactsLocalRepository) {

    suspend operator fun invoke(contactIds: List<Long>): Result<Unit, DataError.LocalError> {
        val localContactsResult = contactsLocalRepository.getContacts().first()
        val errorResult = Result.Error(DataError.LocalError)

        if (localContactsResult is Result.Success) {
            val localContacts = localContactsResult.data
            if (!localContacts.map { contact -> contact.contactInfo.id }.containsAll(contactIds)) {
                return errorResult
            }

            val softDeletedContactsIds = mutableListOf<Long>()
            val hardDeletedContactsIds = mutableListOf<Long>()

            localContacts.forEach { contact ->
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