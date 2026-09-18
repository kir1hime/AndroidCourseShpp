package com.example.androidcourseshpp.domain.usecase.contacts


import com.example.androidcourseshpp.domain.entity.sync.SyncStatus
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.first

class DeleteContactUseCase(
    private val contactsLocalRepository: ContactsLocalRepository
) {

    suspend operator fun invoke(contactId: Long): Result<Unit, DataError.LocalError> {
        val localContactsResult = contactsLocalRepository.getContacts().first()
        val errorResult = Result.Error(DataError.LocalError)

        if (localContactsResult is Result.Success) {
            val localContacts = localContactsResult.data

            val deletedContact =
                localContacts.find { it.contactInfo.id == contactId } ?: return errorResult

            if (deletedContact.syncStatus == SyncStatus.SYNCED) {
                return contactsLocalRepository.setSyncStatus(
                    contactsIds = listOf(contactId),
                   syncStatus =  SyncStatus.DELETED
                )
            }
            if (deletedContact.syncStatus == SyncStatus.ADDED) {
                return contactsLocalRepository.deleteContactById(contactId)
            }
        }

        return errorResult
    }
}
