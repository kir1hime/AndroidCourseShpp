package com.example.androidcourseshpp.ui.screens.main

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.*
import com.example.androidcourseshpp.data.EmailParser
import com.example.androidcourseshpp.ui.screens.contacts.ContactsActivity
import com.example.androidcourseshpp.databinding.ActivityMainBinding
import com.example.androidcourseshpp.ui.extensions.adaptUserInterface
import com.example.androidcourseshpp.ui.utils.factory
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<String>
    private val viewModel by viewModels<MyProfileViewModel> { factory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

        adaptUserInterface(binding.root)

        defineUserName()
        setListeners()
    }

    private fun defineUserName() = with(viewModel.savedEMail) {
        binding.tvName.text = if (value == "") EmailParser.parseEMail(
            intent.getStringExtra(EMAIL_KEY).toString()
        ) else
            EmailParser.parseEMail(value!!)
    }


    private fun setListeners() = with(binding) {
        btLogOut.setOnClickListener {
            moveToSignUpScreen()
            viewModel.deleteUserInfo()

        }
        btViewMyContacts.setOnClickListener {
            requestPermissionsLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun moveToSignUpScreen() {
        val intent = Intent(this, AuthActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.sing_up_fade_in_from_left_to_right,
            R.anim.my_profile_fade_out_from_left_to_right
        )

        startActivity(intent, options.toBundle())
        finish()

    }

    private fun moveToMyContactsScreen(isAccessAllowed: Boolean) {
        val intent = Intent(this, ContactsActivity::class.java)

        intent.putExtra(ACCESS_TO_CONTACTS_KEY, isAccessAllowed)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.contacts_fade_in_from_right_to_left,
            R.anim.my_profile_fade_out_from_right_to_left
        )
        startActivity(intent, options.toBundle())
        finish()
    }

    private fun checkPermissions() {
        requestPermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionsGranted ->
                if (isPermissionsGranted) {
                    moveToMyContactsScreen(true)
                } else {
                    moveToMyContactsScreen(false)
                }
            }
    }
}

