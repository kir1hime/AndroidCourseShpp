package com.example.androidcourseshpp.domain.usecase.contacts


import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

//TODO: rewrite logic using separate method, add sync logic
class DeleteContactsUseCase(private val deleteContactUseCase: DeleteContactUseCase) {
    suspend operator fun invoke(contactIds: List<Long>): Result<Unit, DataError.LocalError> {

        contactIds.forEach { contactId ->
            val result = deleteContactUseCase(contactId)
            if (result is Error) {
                return Result.Error(DataError.LocalError)
            }
        }
        return Result.Success(Unit)

    }
}