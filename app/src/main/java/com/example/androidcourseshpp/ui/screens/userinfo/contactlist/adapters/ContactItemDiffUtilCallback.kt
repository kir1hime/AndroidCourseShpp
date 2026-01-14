package com.example.androidcourseshpp.ui.screens.userinfo.contactlist.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.androidcourseshpp.data.contactlistdata.SelectableContactItem

const val SELECTION_MODE_PAYLOAD = "selectionModePayload"

object ContactItemDiffUtilCallback : DiffUtil.ItemCallback<SelectableContactItem>() {
    override fun areItemsTheSame(
        oldItem: SelectableContactItem,
        newItem: SelectableContactItem
    ): Boolean {
        return oldItem.item.id == newItem.item.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: SelectableContactItem,
        newItem: SelectableContactItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: SelectableContactItem,
        newItem: SelectableContactItem
    ): Any? {
        if (oldItem.item == newItem.item && oldItem.isSelectionModeEnabled != newItem.isSelectionModeEnabled) {
            return SELECTION_MODE_PAYLOAD
        }
        return super.getChangePayload(oldItem, newItem)
    }
}