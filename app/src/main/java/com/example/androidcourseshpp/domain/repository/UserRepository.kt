package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.user.UserItemInfo
import com.example.androidcourseshpp.domain.utils.Result

interface UserRepository {
    suspend fun getUsers(): Result<List<UserItemInfo>>
    suspend fun getUser(userServerId: Int): Result<UserInfo>
    suspend fun updateUserInfo(userInfo: UserInfo): Result<Unit>
}