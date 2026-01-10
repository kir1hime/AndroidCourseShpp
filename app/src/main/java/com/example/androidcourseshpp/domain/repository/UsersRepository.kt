package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.UserItem

interface UsersRepository {
    suspend fun addContact(userItem: UserItem)
    suspend fun loadUsers(): List<UserItem>
}