package com.example.androidcourseshpp.domain.usecase.contacts

import android.util.Log
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
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
    private val contactsNetworkRepository: ContactsNetworkRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {
    operator fun invoke(): Flow<Result<List<ContactInfo>>> = flow {
        if (contactsLocalRepository.isDatabaseSynced()) {
            emitAll(contactsLocalRepository.getContacts())
        } else {
            val serverResult = contactsNetworkRepository.loadContacts()

            serverResult.onSuccess { data ->
                contactsLocalRepository.addContacts(data).onSuccess {
                    contactsLocalRepository.setDatabaseSynced(true)
                    emitAll(contactsLocalRepository.getContacts())
                }.onError {}
            }
        }
    }
}
