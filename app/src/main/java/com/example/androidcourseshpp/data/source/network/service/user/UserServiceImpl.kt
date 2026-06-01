package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.network.entity.user.UpdateUserData
import com.example.androidcourseshpp.data.source.network.api.user.UserAPI
import com.example.androidcourseshpp.data.source.network.service.BaseRetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    private val userApi: UserAPI
) : BaseRetrofitService(), UserService {

    override suspend fun updateUserInfo(userId: Long, userData: UpdateUserData) {
        processRetrofitExceptions {
            userApi.updateUserInfo(
                userId = userData.id,
                updateUserRequestDTO = userData.toUpdateUserDataDTO()
            )
        }
    }

    override suspend fun getUser(userId: Long) = processRetrofitExceptions {
        userApi.getUser(userId).data
    }

    override suspend fun getUsers() = processRetrofitExceptions {
        userApi.getUsers().data
    }
}