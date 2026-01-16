package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteContactsUseCase @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(contacts: List<ContactInfo>) {
        contactsRepository.deleteContacts(contacts)
    }
}