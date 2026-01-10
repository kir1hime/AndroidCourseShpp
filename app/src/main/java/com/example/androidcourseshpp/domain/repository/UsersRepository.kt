package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.ui.screens.main.addcontacts.entity.UserItem

interface UsersRepository {
    suspend fun addContact(userItem: UserItem)
    suspend fun loadUsers(): List<UserItem>
}