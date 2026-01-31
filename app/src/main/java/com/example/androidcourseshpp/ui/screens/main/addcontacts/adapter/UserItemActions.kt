package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import android.widget.ImageView
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.UserItem

interface UserItemActions {
    fun addToContacts(
        userId: Int,
        contactName: String,
        interruptSuccessLoading: () -> Unit,
        interruptFailureLoading: () -> Unit
    )

    fun showUserItemDetails(userItem: UserItem, avatar: ImageView)
}