package com.example.androidcourseshpp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.contactlist.ContactItem
import com.example.androidcourseshpp.contactlist.ContactItemDecoration
import com.example.androidcourseshpp.contactlist.ContactListViewModel
import com.example.androidcourseshpp.contactlist.ContactsAdapter
import com.example.androidcourseshpp.databinding.ActivityContactsBinding

class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactsAdapter
    private lateinit var contactList: List<ContactItem>
    private val viewModel by viewModels<ContactListViewModel>()

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

        contactList = viewModel.contactList.value!!
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = ContactsAdapter(contactList)

        recyclerView = binding.rvContacts
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            ContactItemDecoration(
                resources.getDimensionPixelSize(R.dimen.contacts_recycle_view_space_size_between_items)
            )
        )
    }
}