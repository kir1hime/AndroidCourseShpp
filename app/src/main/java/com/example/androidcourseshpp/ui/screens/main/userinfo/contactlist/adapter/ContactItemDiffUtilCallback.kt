package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.SelectableContactItem

const val SELECTION_MODE_PAYLOAD = "selectionModePayload"

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