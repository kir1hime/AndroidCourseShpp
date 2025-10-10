package com.example.androidcourseshpp.ui.screens.userinfo.contactlist.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.androidcourseshpp.data.contactlist.SelectableContactItem

object ContactItemDiffUtilCallback : DiffUtil.ItemCallback<SelectableContactItem>() {
    override fun areItemsTheSame(oldItem: SelectableContactItem, newItem: SelectableContactItem): Boolean {
        return oldItem.item.id == newItem.item.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SelectableContactItem, newItem: SelectableContactItem): Boolean {
        return oldItem == newItem
    }

}