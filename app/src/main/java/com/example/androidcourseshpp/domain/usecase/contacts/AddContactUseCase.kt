package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.repository.ContactsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddContactUseCase @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(newContactId: Long) {
        contactsRepository.addContact(newContactId)
    }
}