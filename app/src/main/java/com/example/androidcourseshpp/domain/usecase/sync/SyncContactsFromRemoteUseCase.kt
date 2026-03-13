package com.example.androidcourseshpp.domain.usecase.sync

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onError
import com.example.androidcourseshpp.domain.utils.onSuccess
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SyncContactsFromRemoteUseCase @Inject constructor(
    private val contactsLocalRepository: ContactsLocalRepository,
    private val contactsNetworkRepository: ContactsNetworkRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        val result = contactsNetworkRepository.loadContacts()
        var isAllContactsSynced = true

        when (result) {
            is Result.Error -> return result
            is Result.Success -> {
                val serverContacts = result.data
                contactsLocalRepository.getContacts().first()
                    .onSuccess { contacts ->
                        val localContacts = contacts.map { it.contactInfo }

                        val newContacts =
                            serverContacts.filter { contact -> !localContacts.contains(contact) }

                        val deletedContactIds =
                            localContacts.filter { contact -> !serverContacts.contains(contact) }
                                .map { it.id }

                        contactsLocalRepository.refreshContacts(newContacts, deletedContactIds)
                            .onError {
                                isAllContactsSynced = false
                            }

                    }.onError {
                        isAllContactsSynced = false
                    }
            }
        }

        return if (isAllContactsSynced) {
            Result.Success(Unit)
        } else
            Result.Error(AppError.LocalStorageError)
    }
}