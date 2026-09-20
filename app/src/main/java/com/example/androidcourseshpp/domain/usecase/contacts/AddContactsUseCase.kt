package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.entity.sync.SyncStatus
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface AddContactsUseCase {
    suspend operator fun invoke(
        contacts: List<ContactInfo>
    ): Result<Unit, DataError.LocalError>
}

class AddContactsUseCaseImpl(
    private val contactsLocalRepository: ContactsLocalRepository
) : AddContactsUseCase {

    override suspend operator fun invoke(
        contacts: List<ContactInfo>
    ): Result<Unit, DataError.LocalError> {
        return contactsLocalRepository.addContacts(contacts = contacts.map { contact ->
            SyncContactInfo(
                contact,
                SyncStatus.ADDED
            )
        })
    }
}