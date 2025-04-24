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
        const val EMAIL_KEY = "EMAIL_KEY"
        const val PSWD_KEY = "PSWD_KEY"
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
        val userEMail = intent.getStringExtra(EMAIL_KEY)
        binding.tvName.text = EmailParser.parseEMail(userEMail.toString())
    }

    private fun setListeners() {
        binding.btLogOut.setOnClickListener {
            moveToSignUpScreen()
            deleteUserInfo()

        }
    }

    private fun moveToSignUpScreen() {
        val intent = Intent(this@MainActivity, AuthActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            this@MainActivity, R.anim.sing_up_fade_in, R.anim.my_profile_fade_out
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

