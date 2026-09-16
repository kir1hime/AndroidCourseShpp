package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.api.user.UserAPI
import com.example.androidcourseshpp.data.network.model.user.UpdateUserRequestModel
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    private val userApi: UserAPI
) : BaseRetrofitService(), UserService {

    override suspend fun updateUserInfo(data: UpdateUserRequestModel) {
        processRetrofitExceptions {
            userApi.updateUserInfo(
                userId = data.id,
                updateUserRequestDTO = data.toUpdateUserDataDTO()
            )
        }
    }

    override suspend fun getUser(data: Long) = processRetrofitExceptions {
        userApi.getUser(data).data
    }

    override suspend fun getUsers() = processRetrofitExceptions {
        userApi.getUsers().data
    }
}