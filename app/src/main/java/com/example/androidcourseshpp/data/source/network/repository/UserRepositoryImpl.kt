package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.UserModel
import com.example.androidcourseshpp.data.source.network.model.user.toUpdateUserDataModel
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.data.source.network.utils.wrapNetworkExceptions
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.user.UserItemInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val servicesProvider: ServicesProvider,
    private val userDataProvider: UserDataProvider
) : UserRepository {

    override suspend fun getUsers(): Result<List<UserItemInfo>> = wrapNetworkExceptions {
        var userList = emptyList<UserModel>()
        var contactList = emptyList<UserModel>()

        coroutineScope {
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

        userItemList
    }

    override suspend fun getUser(userServerId: Int): Result<UserInfo> = wrapNetworkExceptions {
        servicesProvider.getUserService().getUser(userServerId).user.toUserInfo()
    }

    override suspend fun updateUserInfo(userInfo: UserInfo)  = wrapNetworkExceptions{
        servicesProvider.getUserService().updateUserInfo(userInfo.toUpdateUserDataModel())
    }
}