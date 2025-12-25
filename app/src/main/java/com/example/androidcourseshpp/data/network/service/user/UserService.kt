package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.entity.user.GetUserResponseEntity
import com.example.androidcourseshpp.data.network.entity.user.GetUsersResponseEntity
import com.example.androidcourseshpp.data.network.entity.user.UpdateUserData

interface UserService {

    suspend fun updateUserInfo(userId: Int, userData: UpdateUserData)
    suspend fun getUser(userId: Int): GetUserResponseEntity

    suspend fun getUsers() : GetUsersResponseEntity
}