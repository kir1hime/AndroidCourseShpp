package com.example.androidcourseshpp.data.models.userlist


import kotlinx.coroutines.flow.StateFlow

interface UsersRepository {
    val userList: StateFlow<List<UserItem>>
    suspend fun initUserList()
    suspend fun addContact(userItem: UserItem)
}