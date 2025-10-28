package com.example.androidcourseshpp.data.network.repository.user

import com.example.androidcourseshpp.data.network.repository.user.entity.UpdateUserData

interface UserRepository {

    suspend fun updateUserInfo( userId: Long, userData: UpdateUserData)
}