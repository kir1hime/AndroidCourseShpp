package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.entity.user.GetUserResponseEntity
import com.example.androidcourseshpp.data.network.entity.user.UpdateUserData

interface UserService {

    suspend fun updateUserInfo(userId: Long, userData: UpdateUserData)
    suspend fun getUser(userId: Long): GetUserResponseEntity
}