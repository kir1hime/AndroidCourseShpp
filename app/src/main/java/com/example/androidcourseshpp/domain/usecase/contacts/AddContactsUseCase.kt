package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddContactsUseCase @Inject constructor(
    private val addContactUseCase: AddContactUseCase
) {

    suspend operator fun invoke(contacts: List<ContactInfo>): Result<Unit> {
        contacts.forEach { contact ->
            val result = addContactUseCase(contact)
            if (result is Result.Error) {
                return Result.Error(AppError.LocalStorageError)
            }
        }
        return Result.Success(Unit)
    }
}