package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.entity.UserModel
import com.example.androidcourseshpp.data.source.network.entity.contacts.ContactDataModel
import com.example.androidcourseshpp.data.source.network.entity.user.UpdateUserDataModel
import com.example.androidcourseshpp.data.source.network.service.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.user.UserItemInfo
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
                .addContact(ContactDataModel(userDataProvider.getUserServerId(), newContactId))
        }
    }

    override suspend fun getUsers(): List<UserItemInfo> {
        var userList = emptyList<UserModel>()
        var contactList = emptyList<UserModel>()

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
            user.toUserItemInfo(contactList.contains(user))
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

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        withContext(Dispatchers.IO) {

            with(userInfo) {
                serviceProviderHolder.serviceProvider.getUserService()
                    .updateUserInfo(
                        id, UpdateUserDataModel(
                            name = name,
                            career = career,
                            phone = mobilePhone,
                            address = address,
                            birthday = dateOfBirthday,
                            image = avatar
                        )
                    )
            }
        }
    }
}