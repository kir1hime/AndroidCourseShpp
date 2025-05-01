package com.example.androidcourseshpp.ui.screens.contacts.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.data.ImageLoader
import com.example.androidcourseshpp.databinding.ContactsReyclerviewItemBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL


class ContactsAdapter(private val contacts: List<ContactItem>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    class ViewHolder(private val binding : ContactsReyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContactItem){
            binding.tvName.text = item.name
            binding.tvCareer.text = item.career
            binding.ivAvatar.loadImageFromURL(binding.root.context, item.avatarURL, ImageLoader.PICASSO)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactsReyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount() = contacts.size
}



