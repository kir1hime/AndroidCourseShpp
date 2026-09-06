package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onError
import com.example.androidcourseshpp.domain.utils.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow


class GetContactsUseCase (
    private val contactsNetworkRepository: ContactsNetworkRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {
    operator fun invoke(): Flow<Result<List<SyncContactInfo>>> = flow {
        if (contactsLocalRepository.isDatabaseSynced()) {
            emitAll(contactsLocalRepository.getContacts())
        } else {
            val serverResult = contactsNetworkRepository.loadContacts()

            if (serverResult is Result.Success) {
                contactsLocalRepository.addContacts(serverResult.data)
                    .onSuccess {
                        serverResult.data.forEach { contact ->
                            contactsLocalRepository.setSyncStateToContact(
                                contact.id,
                                SyncAction.SYNCED
                            )
                        }
                        contactsLocalRepository.setDatabaseSynced(true)
                        emitAll(contactsLocalRepository.getContacts())
                    }
                    .onError {
                        emit(Result.Error(AppError.LocalStorageError))
                    }
            } else if (serverResult is Result.Error) {
                emit(Result.Error(serverResult.error))
            }
        }
    }
}