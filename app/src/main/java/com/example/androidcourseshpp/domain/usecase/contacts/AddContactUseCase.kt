package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddContactUseCase @Inject constructor(
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(contactInfo: ContactInfo): Result<Unit> {
        val getContactResult = contactsLocalRepository.getContactById(contactInfo.id)

        if (getContactResult is Result.Success) {
            if (getContactResult.data != null) {
                return contactsLocalRepository.setSyncStateToContact(
                    contactId = contactInfo.id,
                    syncAction = SyncAction.SYNCED
                )
            } else {
                val addContactResult = contactsLocalRepository.addContact(contactInfo)
                if (addContactResult is Result.Success) {
                    return contactsLocalRepository.setSyncStateToContact(
                        contactId = contactInfo.id,
                        syncAction = SyncAction.ADDED
                    )
                }
            }
        }

        return Result.Error(AppError.LocalStorageError)
    }
}