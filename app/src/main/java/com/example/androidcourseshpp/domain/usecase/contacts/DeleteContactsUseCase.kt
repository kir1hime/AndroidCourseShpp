package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsRepository


class DeleteContactsUseCase(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(contacts: List<ContactInfo>) {
        contactsRepository.deleteContacts(contacts)
    }
}