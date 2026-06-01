package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.UserModel
import com.example.androidcourseshpp.data.source.network.model.user.toUpdateUserDataModel
import com.example.androidcourseshpp.data.source.network.service.contacts.ContactsService
import com.example.androidcourseshpp.data.source.network.service.user.UserService
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.user.UserListItemInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val contactsService: ContactsService,
    private val userDataProvider: UserDataProvider
) : UserRepository {

    override suspend fun getUsers(): List<UserListItemInfo> {
        var userList = emptyList<UserModel>()
        var contactList = emptyList<UserModel>()

        withContext(Dispatchers.IO) {
            val usersResponse = async {
                userService.getUsers()
            }
            val contactsResponse = async {
                contactsService.getUserContacts(userDataProvider.getUserServerId())
            }
            userList = usersResponse.await().users
            contactList = contactsResponse.await().contacts
        }

        val userItemList = userList.map { user ->
            user.toUserItemInfo(contactList.contains(user))
        }

        return userItemList
    }

    override suspend fun getUser(userServerId: Long) = withContext(Dispatchers.IO) {
        return@withContext userService.getUser(userServerId).user.toUserInfo()
    }


    override suspend fun updateUserInfo(userInfo: UserInfo) {
        withContext(Dispatchers.IO) {
            userService.updateUserInfo(userInfo.toUpdateUserDataModel())
        }
    }
}