package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.mapper.toUpdateUserModel
import com.example.androidcourseshpp.data.source.network.mapper.toUserInfo
import com.example.androidcourseshpp.data.source.network.service.user.UserService
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.mapResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDataProvider: UserDataProvider
) : UserRepository {

    override suspend fun getUsers(): Result<List<UserInfo>, DataError.NetworkError> {
        val responseResult = userService.getUsers()
        return responseResult.mapResult { result -> result.users.map { user -> user.toUserInfo() } }

    }

    override suspend fun getUser(): Result<UserInfo, DataError.NetworkError> {
        val responseResult = userService.getUser(userId = userDataProvider.getUserServerId())
        return responseResult.mapResult { result -> result.user.toUserInfo() }
    }

    override suspend fun updateUserInfo(userInfo: UserInfo): Result<UserInfo, DataError.NetworkError> {
        val responseResult =
            userService.updateUserInfo(updateUserRequestModel = userInfo.toUpdateUserModel())
        return responseResult.mapResult { result -> result.user.toUserInfo() }
    }
}