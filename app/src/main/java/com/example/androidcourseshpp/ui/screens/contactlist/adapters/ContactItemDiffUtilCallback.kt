package com.example.androidcourseshpp.ui.screens.contactlist.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

object ContactItemDiffUtilCallback : DiffUtil.ItemCallback<ContactItem>() {
    override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem == newItem
    }

}