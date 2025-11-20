package com.example.androidcourseshpp.data.dataProvider

interface DataProvider {
    fun saveUserServerId(userServerId: Long)
    fun getUserServerId(): Long
    fun clearUserServerId()
}