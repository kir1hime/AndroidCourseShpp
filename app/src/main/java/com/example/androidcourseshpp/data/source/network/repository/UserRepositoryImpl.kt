package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.model.user.toUpdateUserDataModel
import com.example.androidcourseshpp.data.source.network.service.user.UserService
import com.example.androidcourseshpp.data.source.network.utils.wrapNetworkExceptions
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDataProvider: UserDataProvider
) : UserRepository {

    override suspend fun getUsers(): Result<List<UserInfo>> = wrapNetworkExceptions {
        userService.getUsers().users.map { it.toUserInfo() }

    }

    override suspend fun getUser(): Result<UserInfo> = wrapNetworkExceptions {
        userService.getUser(userDataProvider.getUserServerId()).user.toUserInfo()
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) = wrapNetworkExceptions {
        userService.updateUserInfo(userInfo.toUpdateUserDataModel())
    }
}