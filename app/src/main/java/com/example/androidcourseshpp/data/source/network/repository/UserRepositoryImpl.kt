package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.entity.User
import com.example.androidcourseshpp.data.source.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.source.network.service.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.domain.entity.UserInfo
import com.example.androidcourseshpp.ui.screens.main.addcontacts.entity.UserItem
import com.example.androidcourseshpp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val serviceProviderHolder: RetrofitServiceProviderHolder,
    private val userDataProvider: UserDataProvider
) : UserRepository {


    override suspend fun addContact(newContactId: Int) {
        withContext(Dispatchers.IO) {
            serviceProviderHolder.serviceProvider.getContactsService()
                .addContact(ContactData(userDataProvider.getUserServerId(), newContactId))
        }
    }

    override suspend fun getUsers(): List<UserInfo> {
        var userList = emptyList<User>()
        var contactList = emptyList<User>()

        withContext(Dispatchers.IO) {
            val usersResponse = async {
                serviceProviderHolder.serviceProvider.getUserService().getUsers()
            }
            val contactsResponse = async {
                serviceProviderHolder.serviceProvider.getContactsService()
                    .getUserContacts(userDataProvider.getUserServerId())
            }
            userList = usersResponse.await().users
            contactList = contactsResponse.await().contacts
        }

        val userItemList = userList.map { user ->
            UserItem(
                id = user.id,
                name = user.name ?: "",
                career = user.career ?: "",
                avatarURL = user.image ?: "",
                isContact = contactList.contains(user)
            )
        }

        return userItemList
    }

    override suspend fun getUser(userServerId: Int): UserInfo {
        val userInfo: UserInfo

        withContext(Dispatchers.IO) {
            userInfo = serviceProviderHolder.serviceProvider.getUserService()
                .getUser(userServerId).user.toUserInfo()
        }
        return userInfo
    }
}