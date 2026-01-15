package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.UserInfo
import com.example.androidcourseshpp.ui.screens.main.addcontacts.entity.UserItem

interface UserRepository {
    suspend fun addContact(userItem: UserItem)
    suspend fun loadUsers(): List<UserItem>

    suspend fun getUser(userServerId: Int): UserInfo
}