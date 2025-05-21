package com.example.androidcourseshpp.ui.screens.contacts

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.*
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

            override fun showUndoDeletingSnackBarContactItem(
                contactItem: ContactItem,
                position: Int
            ) {
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
        setAddContactDialogListener()
    }

    private fun initContactList() {
        adapter.submitList(viewModel.contactList.value!!)
    }

    private fun setObservers() {
        viewModel.contactList.observe(this) {
            adapter.submitList(it)
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
        tvAddContacts.setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun showAddContactDialog() {
        AddContactDialog().show(supportFragmentManager, AddContactDialog.TAG)
    }

    private fun setAddContactDialogListener() {
        supportFragmentManager.setFragmentResultListener(
            AddContactDialog.REQUEST_KEY, this
        ) { _, data ->

            val which = data.getInt(RESPONSE_KEY)
            val newContactName = data.getString(NAME_KEY)
            val newContactCareer = data.getString(CAREER_KEY)

            val newContact = ContactItem(
                viewModel.contactList.value!!.size,
                newContactName!!,
                newContactCareer!!,
                NEW_CONTACT_AVATAR
            )

            when (which) {
                AlertDialog.BUTTON_POSITIVE -> {
                    viewModel.addContactItem(newContact, newContact.id)
                }
            }
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


    private fun showUndoDeletingSnackBarItem(contactItem: ContactItem, position: Int) {
        val undoDeletingSnackBar = Snackbar.make(
            binding.root,
            R.string.snackbar_text,
            Snackbar.LENGTH_LONG
        )

        undoDeletingSnackBar.setAction(R.string.snackbar_action_text) {
            viewModel.addContactItem(contactItem, position)
        }.setActionTextColor(ContextCompat.getColor(this, R.color.custom_primary_color))

        undoDeletingSnackBar.show()
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

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    with(viewHolder) {
                        val deletedItem = viewModel.contactList.value!![adapterPosition]
                        showUndoDeletingSnackBarItem(deletedItem, adapterPosition)
                    }
                    viewModel.deleteContactItem(viewHolder.adapterPosition)
                }
            })

        helper.attachToRecyclerView(binding.rvContacts)
    }
}