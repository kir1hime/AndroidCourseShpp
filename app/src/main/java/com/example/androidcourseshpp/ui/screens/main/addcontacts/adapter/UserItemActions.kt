package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import android.view.View
import android.widget.ImageView
import com.example.androidcourseshpp.data.userlist.UserItem

interface UserItemActions {
    fun addToContacts(userItem: UserItem, interruptProgressBar: () -> Unit)
    fun showUserItemDetails(userItem: UserItem, avatar: ImageView)
}