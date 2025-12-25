package com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.userlist.UserItem
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
                context = root.context,
                url = userItem.avatarURL,
                placeholder = R.drawable.profile_mockup
            )
            imageViewAvatar.transitionName = userItem.id.toString()
            textViewName.text = userItem.name
            textViewCareer.text = userItem.career

            setListeners(userItem)
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
            progressBarRequest.isVisible = true
            actions.addToContacts(userItem) { progressBarRequest.isVisible = false }
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