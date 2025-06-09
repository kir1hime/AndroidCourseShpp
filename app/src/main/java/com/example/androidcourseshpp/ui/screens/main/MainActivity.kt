package com.example.androidcourseshpp.ui.screens.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.EmailParser
import com.example.androidcourseshpp.data.dataProvider.EMAIL_KEY
import com.example.androidcourseshpp.ui.screens.contacts.ContactsActivity
import com.example.androidcourseshpp.databinding.ActivityMainBinding
import com.example.androidcourseshpp.ui.extensions.adaptUserInterface
import com.example.androidcourseshpp.ui.utils.factory
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MyProfileViewModel> { factory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            moveToMyContactsScreen()
        }
    }

    private fun moveToSignUpScreen() {
        val intent = Intent(this, AuthActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_from_left_to_right,
            R.anim.slide_out_from_left_to_right
        )

        startActivity(intent, options.toBundle())
        finish()

    }

    private fun moveToMyContactsScreen() {
        val intent = Intent(this, ContactsActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_from_right_to_left,
            R.anim.slide_out_from_right_to_left
        )
        startActivity(intent, options.toBundle())
        finish()
    }
}

