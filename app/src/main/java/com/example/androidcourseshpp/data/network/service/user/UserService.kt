package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.model.user.GetUserResponseModel
import com.example.androidcourseshpp.data.network.model.user.GetUsersResponseModel
import com.example.androidcourseshpp.data.network.model.user.UpdateUserRequestModel

interface UserService {

    suspend fun updateUserInfo(data: UpdateUserRequestModel)
    suspend fun getUser(data: Long): GetUserResponseModel

    suspend fun getUsers(): GetUsersResponseModel
}