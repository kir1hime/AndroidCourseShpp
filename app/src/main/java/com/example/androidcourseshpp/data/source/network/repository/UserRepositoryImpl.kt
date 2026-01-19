package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.UserModel
import com.example.androidcourseshpp.data.source.network.model.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.model.user.toUpdateUserDataModel
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.user.UserItemInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val servicesProvider: ServicesProvider,
    private val userDataProvider: UserDataProvider
) : UserRepository {


    override suspend fun addContact(newContactId: Int) {
        withContext(Dispatchers.IO) {
            servicesProvider.getContactsService()
                .addContact(ContactDataModel(userDataProvider.getUserServerId(), newContactId))
        }
    }

    override suspend fun getUsers(): List<UserItemInfo> {
        var userList = emptyList<UserModel>()
        var contactList = emptyList<UserModel>()

        withContext(Dispatchers.IO) {
            val usersResponse = async {
                servicesProvider.getUserService().getUsers()
            }
            val contactsResponse = async {
                servicesProvider.getContactsService()
                    .getUserContacts(userDataProvider.getUserServerId())
            }
            userList = usersResponse.await().users
            contactList = contactsResponse.await().contacts
        }

        val userItemList = userList.map { user ->
            user.toUserItemInfo(contactList.contains(user))
        }

        return userItemList
    }

    override suspend fun getUser(userServerId: Int): UserInfo {
        val userInfo: UserInfo

        withContext(Dispatchers.IO) {
            userInfo = servicesProvider.getUserService()
                .getUser(userServerId).user.toUserInfo()
        }
        return userInfo
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        withContext(Dispatchers.IO) {
            with(userInfo) {
                servicesProvider.getUserService()
                    .updateUserInfo(userInfo.toUpdateUserDataModel())
            }
        }
    }
}