package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.source.network.RetrofitConfig
import com.example.androidcourseshpp.data.source.network.service.BaseRetrofitService
import com.example.androidcourseshpp.data.source.network.model.user.UpdateUserDataModel
import com.example.androidcourseshpp.data.source.network.api.user.UserAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitService(config), UserService {

    private val userApi = retrofit.create(UserAPI::class.java)

    override suspend fun updateUserInfo(userData: UpdateUserDataModel) {
        processRetrofitExceptions {
            userApi.updateUserInfo(
                userId = userData.id,
                updateUserRequestDTO = userData.toUpdateUserDataDTO()
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