package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.source.network.api.user.UserAPI
import com.example.androidcourseshpp.data.source.network.model.user.UpdateUserRequestModel
import com.example.androidcourseshpp.data.source.network.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    private val userApi: UserAPI
) : UserService {

    override suspend fun updateUserInfo(data: UpdateUserRequestModel) = safeApiCall {
        userApi.updateUserInfo(
            userId = data.id,
            updateUserRequestDTO = data.toUpdateUserDataDTO()
        )
    }


override suspend fun getUser(data: Long) = safeApiCall {
    userApi.getUser(data).data
}

override suspend fun getUsers() = safeApiCall {
    userApi.getUsers().data
}
}