package com.example.androidcourseshpp.ui.screens.contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.databinding.ContactsRecyclerviewItemBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL


class ContactsAdapter(private val actions: ItemActions) :
    ListAdapter<ContactItem, ContactsAdapter.ViewHolder>(ContactItemDiffUtilCallback) {

    class ViewHolder(
        private val binding: ContactsRecyclerviewItemBinding,
        private val actions: ItemActions
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: ContactItem) = with(binding) {
            tvName.text = item.name
            tvCareer.text = item.career
            ivAvatar.loadImageFromURL(root.context, item.avatarURL)

            ivAvatar.transitionName = item.id.toString()

            ImbDelete.tag = item
            binding.item.tag = item

            setListeners()
        }

        private fun setListeners() = with(binding){
            ImbDelete.setOnClickListener(this@ViewHolder)
            item.setOnClickListener(this@ViewHolder)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.Imb_delete -> {
                    val contactItem = v.tag
                    if (contactItem is ContactItem) {
                        actions.deleteContactItem(contactItem, adapterPosition)
                    }
                }

                R.id.item -> actions.showContactItemDetails()
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



