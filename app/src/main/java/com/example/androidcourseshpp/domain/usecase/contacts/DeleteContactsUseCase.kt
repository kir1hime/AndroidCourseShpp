package com.example.androidcourseshpp.domain.usecase.contacts


import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteContactsUseCase @Inject constructor(
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(contactIds: List<Int>): Result<Unit> {
        return contactsLocalRepository.deleteContactsByIds(contactIds)
    }
}