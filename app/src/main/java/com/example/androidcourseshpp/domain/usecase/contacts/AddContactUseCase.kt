package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddContactUseCase @Inject constructor(
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(contactInfo: ContactInfo): Result<Unit> {
        return contactsLocalRepository.addContact(contactInfo)
    }
}