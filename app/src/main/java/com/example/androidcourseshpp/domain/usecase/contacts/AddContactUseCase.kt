package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.repository.ContactsRepository


class AddContactUseCase(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(newContactId: Long) {
        contactsRepository.addContact(newContactId)
    }
}