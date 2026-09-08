package com.example.androidcourseshpp.domain.usecase.contacts

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

class AddContactsUseCase(
    private val addContactUseCase: AddContactUseCase
) {

    suspend operator fun invoke(contacts: List<ContactInfo>): Result<Unit, DataError.LocalError> {
        contacts.forEach { contact ->
            val result = addContactUseCase(contact)
            if (result is Result.Error<*>) {
                return Result.Error(DataError.LocalError)
            }
        }
        return Result.Success(Unit)
    }
}