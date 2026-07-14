package com.example.androidcourseshpp.domain.usecase.contacts


import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result


class DeleteContactsUseCase(private val deleteContactUseCase: DeleteContactUseCase) {
    suspend operator fun invoke(contactIds: List<Int>): Result<Unit> {

        contactIds.forEach { contactId ->
            val result = deleteContactUseCase(contactId)
            if (result is Result.Error) {
                return Result.Error(
                    AppError.LocalStorageError
                )
            }
        }
        return Result.Success(Unit)

    }
}