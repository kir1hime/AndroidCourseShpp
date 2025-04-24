package com.example.androidcourseshpp.activities

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.validators.SignUpValidator
import com.example.androidcourseshpp.databinding.ActivityAuthBinding
import com.example.androidcourseshpp.extensions.adaptedUserInterface

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(USER_INFO_STORE, MODE_PRIVATE)
    }

    private companion object {
        const val USER_INFO_STORE = "userInfo"
        const val EMAIL_KEY = "EMAIL_KEY"
        const val PSWD_KEY = "PSWD_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat::class.java.adaptedUserInterface(binding.auth)

        if (isExistingAccount()){
            val savedName = sharedPref.getString(EMAIL_KEY, "").toString()
            moveToExistingAccount(savedName)
        }

        setListeners()
    }


    private fun isExistingAccount(): Boolean {
        return sharedPref.getString(EMAIL_KEY, "").toString() != ""
    }

    private fun setListeners() {
        with(binding) {
            binding.btRegister.setOnClickListener {

                if (isEMailCorrect() and isPasswordCorrect()) {
                    if (cbRememberMe.isChecked) {
                        saveUserInfo(etEMail.text.toString(), etPassword.text.toString())
                    }
                    moveToExistingAccount(etEMail.text.toString())
                }
            }
        }
    }

    private fun moveToExistingAccount(userEMail: String) {
        val intent = Intent(this@AuthActivity, MainActivity::class.java)

        intent.putExtra(EMAIL_KEY, userEMail)

        val options = ActivityOptions.makeCustomAnimation(
            this@AuthActivity, R.anim.my_profile_fade_in, R.anim.sing_up_fade_out
        )

        startActivity(intent, options.toBundle())
        finish()
    }

    private fun isEMailCorrect(): Boolean {
        with(binding) {

            val inputEMail: String = etEMail.text.toString()
            val helpMess = SignUpValidator.checkEMail(inputEMail, this@AuthActivity)

            if (helpMess.isNotEmpty()) {
                tilEMail.helperText = helpMess
                return false
            }

            tilEMail.helperText = null
            return true
        }
    }

    private fun isPasswordCorrect(): Boolean {
        with(binding) {

            val inputPassword: String = etPassword.text.toString()
            val helpMess = SignUpValidator.checkPasswordInput(inputPassword, this@AuthActivity)

            if (helpMess.isNotEmpty()) {
                tilPassword.helperText = helpMess
                return false
            }

            tilPassword.helperText = null
            return true
        }
    }

    private fun saveUserInfo(eMail: String, pswd: String) {
        val editor = sharedPref.edit()
        editor.putString(EMAIL_KEY, eMail)
        editor.putString(PSWD_KEY, pswd)
        editor.apply()
    }
}
