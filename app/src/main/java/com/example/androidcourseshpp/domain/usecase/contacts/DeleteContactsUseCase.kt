package com.example.androidcourseshpp.domain.usecase.contacts


import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onError
import com.example.androidcourseshpp.domain.utils.onSuccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(contactIds: List<Int>): Result<Unit> {
        val serverResult = contactsRepository.deleteContacts(contactIds)

        serverResult.onSuccess {
            val localResult = contactsLocalRepository.deleteContactsByIds(contactIds)
            localResult.onError {
                contactsLocalRepository.setDataValidity(false)
            }
        }

        return serverResult
    }
}