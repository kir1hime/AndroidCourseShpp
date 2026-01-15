package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.UserInfo
import com.example.androidcourseshpp.ui.screens.main.addcontacts.entity.UserItem

interface UserRepository {
    suspend fun addContact(newContactId: Int)
    suspend fun getUsers(): List<UserInfo>

    suspend fun getUser(userServerId: Int): UserInfo
}