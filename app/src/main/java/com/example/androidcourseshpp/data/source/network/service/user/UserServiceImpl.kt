package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.source.network.api.user.UserAPI
import com.example.androidcourseshpp.data.source.network.mapper.toGetUserResponseModel
import com.example.androidcourseshpp.data.source.network.mapper.toGetUsersResponseModel
import com.example.androidcourseshpp.data.source.network.mapper.toUpdateUserRequestDTO
import com.example.androidcourseshpp.data.source.network.mapper.toUpdateUserResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.UpdateUserRequestModel
import com.example.androidcourseshpp.data.source.network.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    private val userApi: UserAPI
) : UserService {

    override suspend fun updateUserInfo(updateUserRequestModel: UpdateUserRequestModel) =
        safeApiCall {
            val response = userApi.updateUserInfo(
                userId = updateUserRequestModel.id,
                updateUserRequestDTO = updateUserRequestModel.toUpdateUserRequestDTO()
            )
            response.toUpdateUserResponseModel()
        }


    override suspend fun getUser(userId: Long) = safeApiCall {
        userApi.getUser(userId).toGetUserResponseModel()
    }

    override suspend fun getUsers() = safeApiCall {
        userApi.getUsers().toGetUsersResponseModel()
    }
}