package com.example.androidcourseshpp.data.dataProvider

interface UserDataProvider {
    fun getUserName(): String?

    fun saveUserName(name: String)

    fun clearUserName()
}