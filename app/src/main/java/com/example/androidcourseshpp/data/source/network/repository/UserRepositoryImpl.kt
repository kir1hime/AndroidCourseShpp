package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.network.model.user.toUpdateUserDataModel
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.data.source.network.utils.wrapNetworkExceptions
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val servicesProvider: ServicesProvider
) : UserRepository {

    override suspend fun getUsers(): Result<List<UserInfo>> = wrapNetworkExceptions {
        servicesProvider.getUserService().getUsers().users.map { it.toUserInfo() }

    }

    override suspend fun getUser(userServerId: Int): Result<UserInfo> = wrapNetworkExceptions {
        servicesProvider.getUserService().getUser(userServerId).user.toUserInfo()
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) = wrapNetworkExceptions {
        servicesProvider.getUserService().updateUserInfo(userInfo.toUpdateUserDataModel())
    }
}