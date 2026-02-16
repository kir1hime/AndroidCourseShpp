package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshContactsUseCase @Inject constructor(private val contactsLocalRepository: ContactsLocalRepository) {

    suspend operator fun invoke() {
        contactsLocalRepository.refreshContactList()
    }
}