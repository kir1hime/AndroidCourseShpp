package com.example.androidcourseshpp.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.androidcourseshpp.extensions.adaptedUserInterface
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

    private val recyclerView: RecyclerView by lazy { binding.rvContacts }
    private val adapter: ContactsAdapter by lazy { ContactsAdapter(contactList) }
    private val contactList: List<ContactItem> by lazy { viewModel.contactList.value!! }
    private val viewModel by viewModels<ContactListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat::class.java.adaptedUserInterface(binding.contacts)

        initRecyclerView()

        setListeners()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            ContactItemDecoration(
                resources.getDimensionPixelSize(R.dimen.contacts_recycle_view_space_size_between_items)
            )
        )
    }

    private fun setListeners() = with(binding) {
        IbtArrowBack.setOnClickListener {
            moveToMyProfileScreen()
        }
    }

    private fun moveToMyProfileScreen() {
        val intent = Intent(this, MainActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.my_profile_fade_in_from_left_to_right,
            R.anim.contacts_fade_out_from_left_to_right
        )
        startActivity(intent, options.toBundle())
        finish()
    }
}