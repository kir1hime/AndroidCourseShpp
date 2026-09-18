package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.user.UserListItemInfo
import com.example.androidcourseshpp.domain.entity.user.toUserItemInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetUsersUseCase(
    private val userRepository: UserRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {

     operator fun invoke(): Flow<Result<List<UserListItemInfo>, DataError>> = flow {
        val usersResult = userRepository.getUsers()

        if (usersResult is Result.Success) {
            val localContactsResult = contactsLocalRepository.getContacts().first()

            if (localContactsResult is Result.Success) {
                val localContacts = localContactsResult.data

                emit(usersResult.mapResult { userList ->
                    userList.map { user ->
                        user.toUserItemInfo(
                            isContact = localContacts.map { it.contactInfo.id }.contains(user.id)
                        )
                    }
                })

            } else {
                emit(Result.Error(DataError.LocalError))
            }

        } else {
            emit(usersResult.mapResult { emptyList() })
        }
    }
}