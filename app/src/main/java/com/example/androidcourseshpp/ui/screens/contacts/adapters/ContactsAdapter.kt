package com.example.androidcourseshpp.ui.screens.contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.data.ImageLoader
import com.example.androidcourseshpp.databinding.ContactsRecyclerviewItemBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL


class ContactsAdapter(private val actions: ItemActions) :
    ListAdapter<ContactItem, ContactsAdapter.ViewHolder>(ContactItemDiffUtilCallback) {

    class ViewHolder(
        private val binding: ContactsRecyclerviewItemBinding,
        private val actions: ItemActions
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContactItem) = with(binding) {
            tvName.text = item.name
            tvCareer.text = item.career
            ivAvatar.loadImageFromURL(root.context, item.avatarURL, ImageLoader.PICASSO)
            ImbDelete.tag = item

            ImbDelete.setOnClickListener {
                actions.deleteContactItem(item, adapterPosition)
            }
            binding.item.setOnClickListener{
                actions.showContactItemDetails()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactsRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding, actions)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}



