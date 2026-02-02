package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import android.widget.ImageView
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.UserItem
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.ContactItem

interface UserItemActions {
    fun addToContacts(
        userItem: UserItem,
        interruptSuccessLoading: () -> Unit,
        interruptFailureLoading: () -> Unit
    )

    fun showUserItemDetails(userItem: UserItem, avatar: ImageView)
}