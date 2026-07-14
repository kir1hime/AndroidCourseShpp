package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.entity.user.UserItemInfo
import com.example.androidcourseshpp.domain.entity.user.toUserItemInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUsersUseCase (
    private val userRepository: UserRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {

    suspend operator fun invoke(): Flow<Result<List<UserItemInfo>>> {

        val usersResult = userRepository.getUsers()
        if (usersResult is Result.Error) {
            return flow { emit(Result.Error(usersResult.error)) }
        }
        val users = (usersResult as Result.Success).data
            .map { user -> user.toUserItemInfo(isContact = false) }

        return flow {
            contactsLocalRepository.getContacts().collect { contacts ->
                if (contacts is Result.Error) {
                    emit(Result.Error(contacts.error))
                    return@collect
                }

                val contactIds = (contacts as Result.Success).data
                    .filter { it.syncState != SyncAction.DELETED }
                    .map { it.contactInfo.id }

                val updatedUsers = users.map { user ->
                    user.copy(isContact = contactIds.contains(user.id))
                }
                emit(Result.Success(updatedUsers))
            }
        }
    }
}