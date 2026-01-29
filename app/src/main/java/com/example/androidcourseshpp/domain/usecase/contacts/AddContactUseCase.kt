package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddContactUseCase @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(contact: ContactInfo) {
        contactsRepository.addContact(contact)
    }
}