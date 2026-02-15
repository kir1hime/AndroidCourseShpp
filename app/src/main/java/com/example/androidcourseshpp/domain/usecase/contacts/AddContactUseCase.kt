package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onError
import com.example.androidcourseshpp.domain.utils.onSuccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(contactInfo: ContactInfo): Result<Unit> {
        val serverResult = contactsRepository.addContact(contactInfo.id)

        serverResult.onSuccess {
            val localResult = contactsLocalRepository.addContact(contactInfo)
            localResult.onError {
                contactsLocalRepository.setDataValidity(false)
            }
        }

        return serverResult
    }
}