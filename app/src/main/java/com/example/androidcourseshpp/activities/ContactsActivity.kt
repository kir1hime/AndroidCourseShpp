package com.example.androidcourseshpp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.contactlist.ContactItem
import com.example.androidcourseshpp.contactlist.ContactItemDecoration
import com.example.androidcourseshpp.contactlist.ContactsAdapter
import com.example.androidcourseshpp.databinding.ActivityContactsBinding

class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    private lateinit var contactList: RecyclerView
    private lateinit var adapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contacts)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setContactsList(getContactItems())
    }

    private fun setContactsList(items: List<ContactItem>) {
        adapter = ContactsAdapter(items)

        contactList = binding.rvContacts
        contactList.layoutManager = LinearLayoutManager(this)
        contactList.adapter = adapter
        contactList.addItemDecoration(ContactItemDecoration(resources.getDimensionPixelSize(R.dimen.contacts_recycle_view_space_size_between_items)))
    }

    private fun getContactItems() : List<ContactItem>{
        val items : MutableList<ContactItem> = mutableListOf()

        for (i in 1..30){
            items.add(ContactItem("Denys","Student", R.drawable.profile_photo))
        }

        return items
    }
}