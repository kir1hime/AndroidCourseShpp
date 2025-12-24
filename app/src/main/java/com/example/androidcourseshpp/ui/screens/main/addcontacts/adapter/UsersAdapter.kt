package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.data.userlist.UserItem
import com.example.androidcourseshpp.databinding.AddContactItemBinding
import com.example.androidcourseshpp.ui.utils.loadImageFromURLCircled


class UsersAdapter(private val actions: UserItemActions) :
    ListAdapter<UserItem, UsersAdapter.ViewHolder>(UserItemDIffUtilCallback) {
    inner class ViewHolder(private val binding: AddContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userItem: UserItem) = with(binding) {
            imageViewAvatar.loadImageFromURLCircled(root.context, userItem.avatarURL)
            textViewName.text = userItem.name
            textViewCareer.text = userItem.career

            setListeners(userItem)
        }

        private fun setListeners(userItem: UserItem) = with(binding) {
            textViewAddContact.setOnClickListener {
                actions.addToContacts(userItem)
            }
            imageButtonAddContact.setOnClickListener {
                actions.addToContacts(userItem)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            AddContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}