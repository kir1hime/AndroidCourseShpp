package com.example.androidcourseshpp.activities

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.androidcourseshpp.parsers.EmailParser
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.ActivityMainBinding
import com.example.androidcourseshpp.extensions.adaptedUserInterface

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(USER_INFO_STORE, MODE_PRIVATE)
    }

    companion object {
        const val USER_INFO_STORE = "userInfo"
        const val EMAIL_KEY = "userEMail"
        const val PSWD_KEY = "userPassword"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat::class.java.adaptedUserInterface(binding.main)

        defineUserName()
        setListeners()
    }

    private fun defineUserName() {

        var userEMail = sharedPref.getString(EMAIL_KEY, "")

        if (userEMail == "") {
            userEMail = intent.getStringExtra(EMAIL_KEY)
        }

        binding.tvName.text = EmailParser.parseEMail(userEMail.toString())

    }

    private fun setListeners() = with(binding) {
        btLogOut.setOnClickListener {
            moveToSignUpScreen()
            deleteUserInfo()

        }
        btViewMyContacts.setOnClickListener {
            moveToMyContactsScreen()
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

    private fun moveToMyContactsScreen() {
        val intent = Intent(this, ContactsActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.contacts_fade_in_from_right_to_left,
            R.anim.my_profile_fade_out_from_right_to_left
        )
        startActivity(intent, options.toBundle())
        finish()
    }

    private fun deleteUserInfo() {
        val editor = sharedPref.edit()
        editor.putString(EMAIL_KEY, "")
        editor.putString(PSWD_KEY, "")
        editor.apply()
    }
}

