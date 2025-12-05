package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import com.example.androidcourseshpp.data.network.entity.user.GetUserResponseEntity
import com.example.androidcourseshpp.data.network.entity.user.UpdateUserData
import com.example.androidcourseshpp.data.network.api.user.UserAPI

class UserServiceImpl(
    config: RetrofitConfig
) : BaseRetrofitService(config), UserService {

    private val userApi = retrofit.create(UserAPI::class.java)

    override suspend fun updateUserInfo(userId: Long, userData: UpdateUserData) {
        processRetrofitExceptions {
            userApi.updateUserInfo(
                userId,
                userData.toUpdateUserDataDTO()
            )
        }
    }

    override suspend fun getUser(userId: Long) =
        processRetrofitExceptions {
            GetUserResponseEntity(
                userApi.getUser(userId).data.user
            )
        }
}