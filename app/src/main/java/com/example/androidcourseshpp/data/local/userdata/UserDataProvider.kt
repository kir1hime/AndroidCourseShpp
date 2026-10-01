package com.example.androidcourseshpp.data.local.userdata

interface UserDataProvider {
    suspend fun saveUserServerId(userServerId: Long)
    suspend fun getUserServerId(): Long?
    suspend fun clearUserServerId()
    suspend fun saveUserAvatarUrl(avatarUrl: String)
    suspend fun getUserAvatarUrl(): String?
    suspend fun clearUserAvatarUrl()
    suspend fun isUserRemembered(): Boolean?
    suspend fun setUserRememberState(toSaveUser: Boolean)
    suspend fun clearUserRememberState()
}