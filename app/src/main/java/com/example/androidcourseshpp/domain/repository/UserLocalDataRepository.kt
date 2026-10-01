package com.example.androidcourseshpp.domain.repository

interface UserLocalDataRepository {
    suspend fun saveUserServerId(userServerId: Long)
    suspend fun clearUserServerId()
    suspend fun saveUserAvatarUrl(avatar: String)
    suspend fun getUserAvatarUrl(): String?
    suspend fun clearUserAvatarUrl()
    suspend fun isUserRemembered(): Boolean?
    suspend fun setUserRememberState(toSaveUser: Boolean)
}