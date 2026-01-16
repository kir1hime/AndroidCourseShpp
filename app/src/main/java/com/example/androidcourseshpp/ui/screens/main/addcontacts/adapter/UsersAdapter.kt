package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.UserItem
import com.example.androidcourseshpp.databinding.AddContactItemBinding
import com.example.androidcourseshpp.ui.utils.loadImageFromURLCircled


class UsersAdapter(
    private val actions: UserItemActions,
) :
    ListAdapter<UserItem, UsersAdapter.ViewHolder>(UserItemDIffUtilCallback) {
    inner class ViewHolder(private val binding: AddContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userItem: UserItem) = with(binding) {
            imageViewAvatar.loadImageFromURLCircled(
                root.context, userItem.avatarURL)

            imageViewAvatar.transitionName = userItem.id.toString()
            textViewName.text = userItem.name
            textViewCareer.text = userItem.career

            markContacts(userItem)
            setListeners(userItem)
        }

        private fun markContacts(userItem: UserItem) = with(binding) {
            if (userItem.isContact) {
                imageButtonAddContact.visibility = View.GONE
                imageButtonContactAdded.isVisible = true
                textViewAddContact.visibility = View.GONE
            } else {
                imageButtonAddContact.isVisible = true
                imageButtonContactAdded.isVisible = false
                textViewAddContact.isVisible = true
            }
        }

        private fun setListeners(userItem: UserItem) = with(binding) {
            textViewAddContact.setOnClickListener {
                addContact(userItem)
            }
            imageButtonAddContact.setOnClickListener {
                addContact(userItem)
            }
            addContactItem.setOnClickListener {
                actions.showUserItemDetails(userItem, imageViewAvatar)
            }
        }

        private fun addContact(userItem: UserItem) = with(binding) {
            userItem.isContact = true

            imageButtonAddContact.isVisible = false
            textViewAddContact.isVisible = false
            progressBarAddContact.isVisible = true

            actions.addToContacts(userItem.id) {
                progressBarAddContact.isVisible = false
                imageButtonContactAdded.isVisible = true
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