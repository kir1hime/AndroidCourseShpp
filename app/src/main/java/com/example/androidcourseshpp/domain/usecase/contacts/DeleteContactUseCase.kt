package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteContactUseCase @Inject constructor(
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(contactId: Int): Result<Unit> {
        val getContactResult = contactsLocalRepository.getContactById(contactId)

        if (getContactResult is Result.Success && getContactResult.data != null) {

            val resultData = getContactResult.data

            if (resultData.syncState == SyncAction.SYNCED) {
                return contactsLocalRepository.setSyncStateToContact(
                    contactId = contactId,
                    syncAction = SyncAction.DELETED
                )
            }
            if (resultData.syncState == SyncAction.ADDED) {
                return contactsLocalRepository.deleteContactById(contactId)
            }
        }
        return Result.Error(AppError.LocalStorageError)
    }
}
