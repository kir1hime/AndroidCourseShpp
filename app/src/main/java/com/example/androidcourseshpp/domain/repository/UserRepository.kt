package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface UserRepository {
    suspend fun getUsers(): Result<List<UserInfo>, DataError.Network>
    suspend fun getUser(): Result<UserInfo, DataError.Network>
    suspend fun updateUserInfo(userInfo: UserInfo): Result<UserInfo, DataError.Network>
}