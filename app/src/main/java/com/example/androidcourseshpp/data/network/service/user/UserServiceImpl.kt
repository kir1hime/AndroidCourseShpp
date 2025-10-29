package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import com.example.androidcourseshpp.data.network.service.user.entity.UpdateUserData
import com.example.androidcourseshpp.data.network.webapi.user.UserAPI

class UserServiceImpl(
    config: RetrofitConfig
) : BaseRetrofitService(config), UserService {

    private val userApi = retrofit.create(UserAPI::class.java)

    override suspend fun updateUserInfo(userId: Long, userData: UpdateUserData) {
        processRetrofitExceptions {
            userApi.updateUserInfo(userId, userData.toUpdateUserDataDTO())
        }
    }


}