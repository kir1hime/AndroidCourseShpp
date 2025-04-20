package com.example.androidcourseshpp.contactlist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.ContactsReycleviewItemBinding

class ContactsAdapter(private val contacts: List<ContactItem>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ContactsReycleviewItemBinding.bind(itemView)

        fun bind(item: ContactItem) = with(binding) {
            tvName.text = item.getName()
            tvCareer.text = item.getCareer()
            IvAvatar.setImageResource(item.getAvatarResId())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_reycleview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount() = contacts.size
}
