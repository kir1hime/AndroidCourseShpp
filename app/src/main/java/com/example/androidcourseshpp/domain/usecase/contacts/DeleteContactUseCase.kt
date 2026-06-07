package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.repository.ContactsRepository


class DeleteContactUseCase(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(contactId: Long) {
        contactsRepository.deleteContact(contactId)
    }

}
