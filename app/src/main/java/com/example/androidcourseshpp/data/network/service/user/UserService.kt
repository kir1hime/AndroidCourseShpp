package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.service.user.entity.UpdateUserData

interface UserService {

    suspend fun updateUserInfo( userId: Long, userData: UpdateUserData)
}