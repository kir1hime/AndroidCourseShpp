package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onError
import com.example.androidcourseshpp.domain.utils.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {
    operator fun invoke(): Flow<Result<List<ContactInfo>>> = flow {
        if (contactsLocalRepository.isDataValid()) {
            emitAll(contactsLocalRepository.getContacts())

        } else {
            val serverResult = contactsRepository.loadContacts()

            serverResult.onSuccess { data ->
                contactsLocalRepository.clearContacts()
                contactsLocalRepository.addContacts(data)
                contactsLocalRepository.setDataValidity(true)
                emitAll(contactsLocalRepository.getContacts())
            }.onError { emit(serverResult) }
        }
       /* emit(contactsRepository.loadContacts())*/
    }
}
