package com.example.androidcourseshpp.data.dataProvider

interface UserDataProvider {
    fun saveUserServerId(userServerId: Long)
    fun getUserServerId(): Long
    fun clearUserServerId()
}