package com.example.androidcourseshpp.ui.screens.contacts

import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ContactItemDecoration
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ContactsAdapter
import com.example.androidcourseshpp.databinding.ActivityContactsBinding
import com.example.androidcourseshpp.ui.extensions.adaptUserInterface
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import com.example.androidcourseshpp.ui.utils.factory

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding
    private val viewModel by viewModels<ContactListViewModel> { factory() }

    companion object {
        const val REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptUserInterface(binding.root)


        initRecyclerView()

        setListeners()
    }

    private fun initRecyclerView() = with(binding.rvContacts) {
        layoutManager = LinearLayoutManager(this@ContactsActivity)
        adapter = ContactsAdapter(viewModel.contactList.value!!)

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

    fun checkPermissions(success: () ->  Unit){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            this.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    REQUEST_CODE
                )
            }
        } else {
            success()
        }
    }
}