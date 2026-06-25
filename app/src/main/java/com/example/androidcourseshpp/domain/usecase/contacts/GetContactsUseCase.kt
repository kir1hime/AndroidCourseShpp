package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsRepository


class GetContactsUseCase(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(): List<ContactInfo> {
        return contactsRepository.loadContacts()
    }
}