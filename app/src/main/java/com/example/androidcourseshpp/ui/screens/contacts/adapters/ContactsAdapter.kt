package com.example.androidcourseshpp.ui.screens.contacts.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.data.ImageLoader
import com.example.androidcourseshpp.databinding.ContactsRecyclerviewItemBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL


class ContactsAdapter(private val actionListener: ContactItemActionListener) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>(), View.OnClickListener {

    var contactList: List<ContactItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(private val binding: ContactsRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContactItem) = with(binding) {
            tvName.text = item.name
            tvCareer.text = item.career
            ivAvatar.loadImageFromURL(root.context, item.avatarURL, ImageLoader.PICASSO)
            ImbDelete.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactsRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        binding.ImbDelete.setOnClickListener(this)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount() = contactList.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(v: View) {

        val contactItem = v.tag as ContactItem

        if (v.id == R.id.Imb_delete) {
            actionListener.deleteContactItem(contactItem)
            actionListener.cancelDeletingContactItem(contactItem, contactItem.id)
            notifyDataSetChanged()
        }

    }
}



