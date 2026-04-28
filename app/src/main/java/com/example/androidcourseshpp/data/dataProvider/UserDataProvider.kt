package com.example.androidcourseshpp.data.dataProvider

interface UserDataProvider {
    fun saveUserServerId(userServerId: Long)
    fun getUserServerId(): Long
    fun clearUserServerId()
    fun saveUserAvatarUrl(avatar: String)
    fun getUserAvatarUrl(): String
    fun clearUserAvatarUrl()
}