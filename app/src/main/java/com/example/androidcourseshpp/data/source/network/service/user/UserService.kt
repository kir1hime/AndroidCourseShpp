package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.source.network.model.user.GetUserResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.GetUsersResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.UpdateUserRequestModel

interface UserService {

    suspend fun updateUserInfo(data: UpdateUserRequestModel)
    suspend fun getUser(data: Long): GetUserResponseModel

    suspend fun getUsers(): GetUsersResponseModel
}