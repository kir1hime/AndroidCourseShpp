package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.user.UserListItem
import com.example.androidcourseshpp.domain.entity.user.toUserItem
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

interface GetUsersUseCase {
    operator fun invoke(): Flow<Result<List<UserListItem>, DataError>>
}

class GetUsersUseCaseImpl(
    private val userRepository: UserRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) : GetUsersUseCase {

    override operator fun invoke(): Flow<Result<List<UserListItem>, DataError>> = flow {
        val usersResult = userRepository.getUsers()

        if (usersResult is Result.Success) {
            val localContactsResult = contactsLocalRepository.getContacts().first()

            if (localContactsResult is Result.Success) {
                val localContacts = localContactsResult.data
                val localContactsIds = localContacts.map { contact -> contact.contactInfo.id }
                val isContact: (Long) -> Boolean = { id -> localContactsIds.contains(id) }

                val usersList = usersResult.mapResult { usersList ->
                    usersList.map { user -> user.toUserItem(isContact(user.id)) }
                }

                emit(usersList)

            } else {
                emit(Result.Error(DataError.LocalError))
            }

        } else {
            emit(usersResult.mapResult { emptyList() })
        }
    }
}