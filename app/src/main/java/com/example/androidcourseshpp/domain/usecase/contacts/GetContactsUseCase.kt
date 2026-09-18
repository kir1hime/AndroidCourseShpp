package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetContactsUseCase(
    private val contactsLocalRepository: ContactsLocalRepository
) {
    operator fun invoke(): Flow<Result<List<ContactInfo>, DataError>> {
        return contactsLocalRepository.getContacts()
            .map { result ->
                result.mapResult { contactsList ->
                    contactsList.map { contact -> contact.contactInfo }
                }
            }
    }
}