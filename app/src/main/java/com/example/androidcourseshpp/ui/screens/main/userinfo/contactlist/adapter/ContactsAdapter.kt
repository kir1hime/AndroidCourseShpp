package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.ContactItem
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.SelectableContactItem
import com.example.androidcourseshpp.databinding.ContactItemBinding
import com.example.androidcourseshpp.ui.utils.loadImageFromURLCircled


class ContactsAdapter(private val actions: ContactItemActions) :
    ListAdapter<SelectableContactItem, ContactsAdapter.ViewHolder>(ContactItemDiffUtilCallback) {

    var selectedItems: MutableList<ContactItem> = mutableListOf()
        private set

    inner class ViewHolder(
        private val binding: ContactItemBinding,
        private val actions: ContactItemActions
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contactItem: SelectableContactItem) = with(binding) {
            textViewName.text = contactItem.item.name
            textViewCareer.text = contactItem.item.career
            imageViewAvatar.loadImageFromURLCircled(root.context, contactItem.item.avatarURL)

            imageViewAvatar.transitionName = contactItem.item.id.toString()

            switchComponentsVisibility(contactItem)

            checkBoxIsSelected.isChecked = selectedItems.contains(contactItem.item)

            setListeners(contactItem)
        }

        private fun switchComponentsVisibility(contactItem: SelectableContactItem) = with(binding) {

            if (contactItem.isSelectMode) {
                contactListItem.setBackgroundResource(R.drawable.contacts_item_background_selected_mode)
                checkBoxIsSelected.visibility = View.VISIBLE
                imageButtonDelete.visibility = View.GONE

            } else {
                contactListItem.setBackgroundResource(R.drawable.contacts_item_background_unselected_mode)
                checkBoxIsSelected.visibility = View.GONE
                imageButtonDelete.visibility = View.VISIBLE

                /*  actions.hideFloatingDeleteButton()*/
                selectedItems.clear()
            }
        }

        private fun setListeners(contactItem: SelectableContactItem) = with(binding) {
            imageButtonDelete.setOnClickListener {
                actions.deleteContactItem(contactItem.item, adapterPosition)
            }
            contactListItem.setOnClickListener {

                if (contactItem.isSelectMode) {
                    onItemClickListenerInSelectableMode(contactItem)

                } else {
                    actions.showContactItemDetails(contactItem.item, binding.imageViewAvatar)
                }
            }

            checkBoxIsSelected.setOnClickListener {
                onItemClickListenerInSelectableMode(contactItem)
            }

            contactListItem.setOnLongClickListener {
                /* actions.showFloatingDeleteButton()
                 actions.showCancelSelectTextView()*/
                selectedItems.add(contactItem.item)
                changeMode(true)
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

        private fun changeMode(isSelectMode: Boolean) {
            actions.selectModeChangingListener(isSelectMode)
            val newList = currentList.map { it.copy(isSelectMode = isSelectMode) }
            submitList(newList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding.inflate(
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



