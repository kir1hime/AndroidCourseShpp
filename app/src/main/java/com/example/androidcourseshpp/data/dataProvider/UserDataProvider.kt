package com.example.androidcourseshpp.data.dataProvider

interface UserDataProvider {
    fun getUserServerId(): Long

    fun saveUserServerId(userServerId: Long)

    fun clearUserServerId()
}