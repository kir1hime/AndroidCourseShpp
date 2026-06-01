package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.source.network.model.user.GetUserResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.GetUsersResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.UpdateUserDataModel

interface UserService {

    suspend fun updateUserInfo(userData: UpdateUserDataModel)
    suspend fun getUser(userId: Long): GetUserResponseModel

    suspend fun getUsers(): GetUsersResponseModel
}