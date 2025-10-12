package com.example.androidcourseshpp.ui.screens.auth.chooseprofilephoto.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.androidcourseshpp.data.gallery.GalleryItem

object GalleryItemDiffUtilCallback : DiffUtil.ItemCallback<GalleryItem>() {
    override fun areItemsTheSame(
        oldItem: GalleryItem,
        newItem: GalleryItem
    ): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GalleryItem,
        newItem: GalleryItem
    ): Boolean {

        return oldItem == newItem
    }
}