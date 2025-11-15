package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.dto.entity.User
import com.example.androidcourseshpp.data.network.service.user.entity.UpdateUserData

interface UserService {

    suspend fun updateUserInfo(userId: Long, userData: UpdateUserData): User
    suspend fun getUser(userId: Long): User
}