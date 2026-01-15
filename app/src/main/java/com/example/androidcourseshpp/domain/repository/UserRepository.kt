package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.user.UserItemInfo

interface UserRepository {
    suspend fun addContact(newContactId: Int)
    suspend fun getUsers(): List<UserItemInfo>

    suspend fun getUser(userServerId: Int): UserInfo
}