package com.example.androidcourseshpp.contactlist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import de.hdodenhof.circleimageview.CircleImageView

class ContactsAdapter(private val contacts: List<ContactItem>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tv_name)
        val itemCareer: TextView = itemView.findViewById(R.id.tv_career)
        val itemAvatar: CircleImageView = itemView.findViewById(R.id.Iv_avatar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_reycleview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.itemName.text = contacts[position].getName()
       holder.itemCareer.text = contacts[position].getCareer()
       holder.itemAvatar.setImageResource(contacts[position].getAvatarResId())
    }

    override fun getItemCount() = contacts.size
}