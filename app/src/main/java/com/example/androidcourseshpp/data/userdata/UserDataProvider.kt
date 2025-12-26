package com.example.androidcourseshpp.data.userdata

interface UserDataProvider {
    fun saveUserServerId(userServerId: Int)
    fun getUserServerId(): Int
    fun clearUserServerId()
    fun saveUserAvatarUrl(avatar: String)
    fun getUserAvatarUrl(): String
    fun clearUserAvatarUrl()
}