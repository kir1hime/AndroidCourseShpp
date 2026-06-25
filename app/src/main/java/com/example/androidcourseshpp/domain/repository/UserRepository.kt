package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.user.UserListItemInfo

interface UserRepository {
    suspend fun getUsers(): List<UserListItemInfo>
    suspend fun getUser(userServerId: Long): UserInfo
    suspend fun updateUserInfo(userInfo: UserInfo)
}