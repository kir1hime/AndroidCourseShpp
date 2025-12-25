package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import com.example.androidcourseshpp.data.userlist.UserItem

interface UserItemActions {
    fun addToContacts(userItem: UserItem)
    fun showUserItemDetails(userItem: UserItem)
}