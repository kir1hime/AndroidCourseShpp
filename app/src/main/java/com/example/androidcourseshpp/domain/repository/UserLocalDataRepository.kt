package com.example.androidcourseshpp.domain.repository

interface UserLocalDataRepository {
    fun saveUserServerId(userServerId: Long)
    fun getUserServerId(): Long
    fun clearUserServerId()
    fun saveUserAvatarUrl(avatar: String)
    fun getUserAvatarUrl(): String
    fun clearUserAvatarUrl()
    fun isUserRemembered(): Boolean
    fun setUserRememberState(toSaveUser: Boolean)
}