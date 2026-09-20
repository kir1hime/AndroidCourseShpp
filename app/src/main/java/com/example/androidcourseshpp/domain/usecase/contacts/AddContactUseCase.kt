package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.entity.sync.SyncStatus
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface AddContactUseCase {
    suspend operator fun invoke(
        contactInfo: ContactInfo
    ): Result<Unit, DataError.LocalError>
}

class AddContactUseCaseImpl(
    private val contactsLocalRepository: ContactsLocalRepository
) : AddContactUseCase {

    override suspend operator fun invoke(contactInfo: ContactInfo): Result<Unit, DataError.LocalError> {
        return contactsLocalRepository.addContact(
            SyncContactInfo(
                contactInfo = contactInfo,
                SyncStatus.ADDED
            )
        )
    }
}
