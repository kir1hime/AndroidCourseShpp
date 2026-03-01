package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.entity.user.UserItemInfo
import com.example.androidcourseshpp.domain.entity.user.toUserItemInfo
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {

    suspend operator fun invoke(): Result<List<UserItemInfo>> = coroutineScope {
        val contactIdsDeferred = async {
            var contactIds = emptyList<Int>()
            contactsLocalRepository.getContacts().first().onSuccess { contactList ->
                contactIds =
                    contactList.filter { contact -> contact.syncState != SyncAction.DELETED }
                        .map { contact -> contact.contactInfo.id }
            }
            contactIds
        }
        val usersDeferred = async { userRepository.getUsers() }

        val contactIds = contactIdsDeferred.await()
        val usersResult = usersDeferred.await()
        var users = emptyList<UserItemInfo>()

        val result = usersResult.onSuccess { userList ->
            users = userList.map { user -> user.toUserItemInfo(contactIds.contains(user.id)) }
        }

        if (result is Result.Error) Result.Error(result.error) else Result.Success(users)

    }
}