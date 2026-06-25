package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.UserItem

object UserItemDIffUtilCallback : DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(
        oldItem: UserItem,
        newItem: UserItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserItem,
        newItem: UserItem
    ): Boolean {
       return oldItem == newItem
    }
}