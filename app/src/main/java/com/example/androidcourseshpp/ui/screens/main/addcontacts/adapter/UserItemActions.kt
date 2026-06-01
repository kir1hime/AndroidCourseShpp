package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import android.widget.ImageView
import com.example.androidcourseshpp.data.models.userlist.UserItem

interface UserItemActions {
    fun addToContacts(userItem: UserItem, interruptLoading: () -> Unit)
    fun showUserItemDetails(userItem: UserItem, avatar: ImageView)
}