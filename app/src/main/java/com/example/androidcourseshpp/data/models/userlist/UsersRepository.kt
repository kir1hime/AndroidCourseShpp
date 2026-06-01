package com.example.androidcourseshpp.data.models.userlist

interface UsersRepository {
    suspend fun addContact(userItem: UserItem)
    suspend fun loadUsers(): List<UserItem>
}