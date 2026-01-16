package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.source.network.entity.user.GetUserResponseModel
import com.example.androidcourseshpp.data.source.network.entity.user.UpdateUserDataModel
import com.example.androidcourseshpp.data.source.network.model.user.GetUsersResponseModel

interface UserService {

    suspend fun updateUserInfo(userId: Int, userData: UpdateUserDataModel)
    suspend fun getUser(userId: Int): GetUserResponseModel

    suspend fun getUsers() : GetUsersResponseModel
}