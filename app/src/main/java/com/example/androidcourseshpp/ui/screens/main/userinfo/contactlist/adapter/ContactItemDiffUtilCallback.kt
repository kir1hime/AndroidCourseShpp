package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.entity.SelectableContactItem

object ContactItemDiffUtilCallback : DiffUtil.ItemCallback<SelectableContactItem>() {
    override fun areItemsTheSame(
        oldItem: SelectableContactItem,
        newItem: SelectableContactItem
    ): Boolean {
        return oldItem.item.id == newItem.item.id
    }

    override fun areContentsTheSame(
        oldItem: SelectableContactItem,
        newItem: SelectableContactItem
    ): Boolean {
        return oldItem == newItem
    }

}