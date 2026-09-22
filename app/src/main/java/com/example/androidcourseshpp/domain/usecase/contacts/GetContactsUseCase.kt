package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.sync.SyncStatus
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetContactsUseCase {
    operator fun invoke(): Flow<Result<List<ContactInfo>, DataError>>
}

class GetContactsUseCaseImpl(
    private val contactsLocalRepository: ContactsLocalRepository
) : GetContactsUseCase {

    override operator fun invoke(): Flow<Result<List<ContactInfo>, DataError>> {
        return contactsLocalRepository.getContacts()
            .map { result ->
                result.mapResult { contactsList ->
                    contactsList.filter { contact -> contact.syncStatus != SyncStatus.DELETED }
                        .map { contact -> contact.contactInfo }
                }
            }
    }
}