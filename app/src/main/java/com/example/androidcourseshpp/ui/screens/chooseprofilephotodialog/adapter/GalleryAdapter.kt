package com.example.androidcourseshpp.ui.screens.chooseprofilephotodialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.gallery.GalleryItem
import com.example.androidcourseshpp.databinding.GalleryItemBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL

class GalleryAdapter(private val actions: ItemActions) :
    ListAdapter<GalleryItem, GalleryAdapter.ViewHolder>(
        GalleryItemDiffUtilCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, actions)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }


    class ViewHolder(val binding: GalleryItemBinding, val actions: ItemActions) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(galleryItem: GalleryItem) = with(binding) {
            imageViewPhoto.loadImageFromURL(
                root.context,
                galleryItem.photoURL,
                R.drawable.ic_camera
            )
            setListeners(galleryItem.photoURL)
        }

        private fun setListeners(photo: String) {
            binding.imageViewPhoto.setOnClickListener {
                actions.choosePhoto(photo)
            }
        }
    }
}