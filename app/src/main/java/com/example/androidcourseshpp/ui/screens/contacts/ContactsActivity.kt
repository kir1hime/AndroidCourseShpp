package com.example.androidcourseshpp.ui.screens.contacts

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.ACCESS_TO_CONTACTS_KEY
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ContactItemDecoration
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ContactsAdapter
import com.example.androidcourseshpp.databinding.ActivityContactsBinding
import com.example.androidcourseshpp.ui.extensions.adaptUserInterface
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ContactItemActionListener
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import com.example.androidcourseshpp.ui.utils.factory
import com.google.android.material.snackbar.Snackbar

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding

    private val viewModel by viewModels<ContactListViewModel> { factory() }

    private val adapter by lazy {
        ContactsAdapter(object : ContactItemActionListener {
            override fun deleteContactItem(contactItem: ContactItem) {
                viewModel.deleteContactItem(contactItem)
            }

            override fun cancelDeletingContactItem(contactItem: ContactItem, position: Int) {
                showUndoDeletingSnackBarItem(contactItem, position)
            }
        }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptUserInterface(binding.root)

        initContactList()
        initRecyclerView()
        initSwipeToDeleteOfContactItem()

        setObservers()
        setListeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initContactList() {
        adapter.contactList = viewModel.contactList.value!!
    }

    private fun setObservers() {
        viewModel.contactList.observe(this) {
            adapter.contactList = it
        }
    }

    fun isAccessToContactsAllowed(): Boolean {
        return intent.getBooleanExtra(ACCESS_TO_CONTACTS_KEY, false)
    }

    private fun initRecyclerView() = with(binding.rvContacts) {
        layoutManager = LinearLayoutManager(this@ContactsActivity)
        adapter = this@ContactsActivity.adapter

        addItemDecoration(
            ContactItemDecoration(
                resources.getDimensionPixelSize(R.dimen.contacts_recycle_view_space_size_between_items)
            )
        )
    }

    private fun setListeners() = with(binding) {
        ibtArrowBack.setOnClickListener {
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

    @SuppressLint("NotifyDataSetChanged")
    private fun showUndoDeletingSnackBarItem(contactItem: ContactItem, position: Int) {
        val deletingSnackBar = Snackbar.make(
            binding.root,
            R.string.snackbar_text,
            Snackbar.LENGTH_LONG
        )

        deletingSnackBar.setAction(R.string.snackbar_action_text) {
            viewModel.addContactItem(contactItem, position)
            adapter.notifyDataSetChanged()
        }

        deletingSnackBar.show()
    }

    private fun initSwipeToDeleteOfContactItem() {
        val helper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    with(viewHolder) {
                        val deletedItem = adapter.contactList[adapterPosition]
                        showUndoDeletingSnackBarItem(deletedItem, adapterPosition)
                    }

                    viewModel.deleteContactItem(viewHolder.adapterPosition)
                    adapter.notifyDataSetChanged()
                }

            })
        helper.attachToRecyclerView(binding.rvContacts)

    }


}