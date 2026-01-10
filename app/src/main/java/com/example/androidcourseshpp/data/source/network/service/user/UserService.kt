package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.source.network.entity.user.GetUserResponseEntity
import com.example.androidcourseshpp.data.source.network.entity.user.GetUsersResponseEntity
import com.example.androidcourseshpp.data.source.network.entity.user.UpdateUserData

interface UserService {

    suspend fun updateUserInfo(userId: Int, userData: UpdateUserData)
    suspend fun getUser(userId: Int): GetUserResponseEntity

    suspend fun getUsers() : GetUsersResponseEntity
}