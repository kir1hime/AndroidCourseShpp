package com.example.androidcourseshpp.data.network.repository.user

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.repository.BaseRetrofitRepository
import com.example.androidcourseshpp.data.network.repository.user.entity.UpdateUserData
import com.example.androidcourseshpp.data.network.webapi.user.UserAPI

class UserRepositoryImpl(
    config: RetrofitConfig
) : BaseRetrofitRepository(config), UserRepository {

    private val userApi = retrofit.create(UserAPI::class.java)

    override suspend fun updateUserInfo(userId: Long, userData: UpdateUserData) {
        processRetrofitExceptions {
            userApi.updateUserInfo(userId, userData.toUpdateUserDataDTO())
        }
    }


}