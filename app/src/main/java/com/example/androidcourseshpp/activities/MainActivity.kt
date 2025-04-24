package com.example.androidcourseshpp.activities

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
            moveToAuthActivity()
            deleteUserInfo()

        }
    }

    private fun moveToAuthActivity() {
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

    object EmailParser {

        fun parseEMail(eMail: String): String {
            val parsedName = StringBuilder()

            val parsedEmail: MutableList<String> = eMail.split('@').toMutableList()
            val name = parsedEmail[0].filter { it.isLetter() || it == '.' }.split('.')

            parsedName.append(name[0])
            if (name.size > 1) {
                parsedName.append(" ").append(name[1])
            }

            parsedName.capitalize()

            return parsedName.toString()
        }

        private fun StringBuilder.capitalize() {
            this[0] = this[0].uppercaseChar()
            this[this.indexOf(' ') + 1] =
                this[this.indexOf(' ') + 1].uppercaseChar()
        }
    }
}

