package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface UserRepository {
    suspend fun getUsers(): Result<List<UserInfo>, DataError.NetworkError>
    suspend fun getUser(): Result<UserInfo, DataError.NetworkError>
    suspend fun updateUserInfo(userInfo: UserInfo): Result<Unit, DataError.NetworkError>
}