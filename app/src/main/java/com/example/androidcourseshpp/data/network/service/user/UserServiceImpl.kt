package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import com.example.androidcourseshpp.data.network.entity.user.UpdateUserData
import com.example.androidcourseshpp.data.network.api.user.UserAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitService(config), UserService {

    private val userApi = retrofit.create(UserAPI::class.java)

    override suspend fun updateUserInfo(userId: Int, userData: UpdateUserData) {
        processRetrofitExceptions {
            userApi.updateUserInfo(
                userId,
                userData.toUpdateUserDataDTO()
            )
        }
    }

    override suspend fun getUser(userId: Int) = processRetrofitExceptions {
        userApi.getUser(userId).data
    }

    override suspend fun getUsers() = processRetrofitExceptions {
        userApi.getUsers().data
    }
}