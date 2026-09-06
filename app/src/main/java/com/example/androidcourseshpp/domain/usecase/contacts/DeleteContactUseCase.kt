package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result

class DeleteContactUseCase(private val contactsLocalRepository: ContactsLocalRepository) {
    suspend operator fun invoke(contactId: Long): Result<Unit> {
        val getContactResult = contactsLocalRepository.getContactById(contactId)

        if (getContactResult is Result.Success && getContactResult.data != null) {

            val dataSyncState = getContactResult.data.syncState

            return when (dataSyncState) {
                SyncAction.SYNCED -> contactsLocalRepository.setSyncStateToContact(
                    contactId = contactId,
                    syncAction = SyncAction.DELETED
                )

                else -> contactsLocalRepository.deleteContactById(contactId)

            }

        }
        return Result.Error(AppError.LocalStorageError)
    }
}
