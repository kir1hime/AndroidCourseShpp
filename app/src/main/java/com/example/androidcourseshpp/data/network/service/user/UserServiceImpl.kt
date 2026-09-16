package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.api.user.UserAPI
import com.example.androidcourseshpp.data.network.mapper.toGetUserResponseModel
import com.example.androidcourseshpp.data.network.mapper.toGetUsersResponseModel
import com.example.androidcourseshpp.data.network.mapper.toUpdateUserRequestDTO
import com.example.androidcourseshpp.data.network.model.user.UpdateUserRequestModel
import com.example.androidcourseshpp.data.network.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    private val userApi: UserAPI
) : UserService {

    override suspend fun updateUserInfo(updateUserRequestModel: UpdateUserRequestModel) =
        safeApiCall {
            userApi.updateUserInfo(
                userId = updateUserRequestModel.id,
                updateUserRequestDTO = updateUserRequestModel.toUpdateUserRequestDTO()
            )
        }


    override suspend fun getUser(userId: Long) = safeApiCall {
        userApi.getUser(userId).toGetUserResponseModel()
    }

    override suspend fun getUsers() = safeApiCall {
        userApi.getUsers().toGetUsersResponseModel()
    }
}