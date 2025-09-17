package com.example.androidcourseshpp.ui.screens.userinfo.contactlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.data.contactlistdata.SelectableContactItem
import com.example.androidcourseshpp.databinding.ContactsRecyclerviewItemBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL


class ContactsAdapter(private val actions: ItemActions) :
    ListAdapter<SelectableContactItem, ContactsAdapter.ViewHolder>(ContactItemDiffUtilCallback) {

    var selectedItems: MutableList<ContactItem> = mutableListOf()
        private set

    inner class ViewHolder(
        private val binding: ContactsRecyclerviewItemBinding,
        private val actions: ItemActions
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contactItem: SelectableContactItem) = with(binding) {
            textViewName.text = contactItem.item.name
            textViewCareer.text = contactItem.item.career
            imageViewAvatar.loadImageFromURL(root.context, contactItem.item.avatarURL)

            imageViewAvatar.transitionName = contactItem.item.id.toString()

            switchComponentsVisibility(contactItem)

            checkBoxIsSelected.isChecked = selectedItems.contains(contactItem.item)

            setListeners(contactItem)
        }

        private fun switchComponentsVisibility(contactItem: SelectableContactItem) = with(binding) {

            if (contactItem.isSelectionModeEnabled) {
                contactListItem.setBackgroundResource(R.drawable.contacts_item_background_selected_mode)
                checkBoxIsSelected.visibility = View.VISIBLE
                imageButtonDelete.visibility = View.GONE

            } else {
                contactListItem.setBackgroundResource(R.drawable.contacts_item_background_unselected_mode)
                checkBoxIsSelected.visibility = View.GONE
                imageButtonDelete.visibility = View.VISIBLE

                actions.hideFloatingDeleteButton()
                selectedItems.clear()
            }
        }

        private fun setListeners(contactItem: SelectableContactItem) = with(binding) {
            imageButtonDelete.setOnClickListener {
                actions.deleteContactItem(contactItem.item, adapterPosition)
            }
            contactListItem.setOnClickListener {

                if (contactItem.isSelectionModeEnabled) {
                    onItemClickListenerInSelectableMode(contactItem)

                } else {
                    actions.showContactItemDetails(contactItem.item, binding.imageViewAvatar)
                }
            }

            checkBoxIsSelected.setOnClickListener {
                onItemClickListenerInSelectableMode(contactItem)
            }

            contactListItem.setOnLongClickListener {
                actions.showFloatingDeleteButton()
                onLongClickListener(contactItem)
                true
            }
        }

        private fun onItemClickListenerInSelectableMode(contactItem: SelectableContactItem) {

            if (!selectedItems.contains(contactItem.item)) {
                binding.checkBoxIsSelected.isChecked = true
                selectedItems.add(contactItem.item)

            } else {
                selectedItems.remove(contactItem.item)
                binding.checkBoxIsSelected.isChecked = false
                if (selectedItems.isEmpty()) {
                    changeMode(false)
                }
            }
        }

        private fun onLongClickListener(contactItem: SelectableContactItem) {
            selectedItems.add(contactItem.item)
            changeMode(true)
        }

        private fun changeMode(selectionModeEnabled: Boolean) {
            val newList = currentList.map { it.copy(isSelectionModeEnabled = selectionModeEnabled) }
            submitList(newList)
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



