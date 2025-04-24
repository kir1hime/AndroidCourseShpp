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
        const val EMAIL_KEY = "userEMail"
        const val PASSWORD_KEY = "userPassword"
        const val MIN_NUM_OF_CHARS_IN_PASSWORD = 8
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat::class.java.adaptedUserInterface(binding.auth)

        if (isExistingAccount()) {
            val savedName = sharedPref.getString(EMAIL_KEY, "").toString()
            moveToExistingAccount(savedName)
        }

        setListeners()
    }


    private fun isExistingAccount(): Boolean {
        return sharedPref.getString(EMAIL_KEY, "").toString() != ""
    }

    private fun setListeners() = with(binding) {
        btRegister.setOnClickListener {

            if ((defineEMailErrorMassage() == "") and (definePasswordErrorMassage() == "")) {
                if (cbRememberMe.isChecked) {
                    saveUserInfo(etEMail.text.toString(), etPassword.text.toString())
                }
                moveToExistingAccount(etEMail.text.toString())
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

    private fun defineEMailErrorMassage(): String = with(binding) {
        var errorMassage = ""
        val inputEMail: String = etEMail.text.toString()

        if (!SignUpValidator.checkEMail(inputEMail)) {
            errorMassage = getString(R.string.email_error)
            tilEMail.helperText = errorMassage
            return errorMassage
        }

        tilEMail.helperText = null

        return errorMassage
    }

    private fun definePasswordErrorMassage(): String = with(binding) {
        var errorMassage = ""

        val inputPassword: String = etPassword.text.toString()

        val passwordChecks: MutableList<(s: String) -> Boolean> =
            mutableListOf(
                SignUpValidator::checkPasswordForNumOfLetters,
                SignUpValidator::checkPasswordForUpperCase,
                SignUpValidator::checkPasswordForLowerCase,
                SignUpValidator::checkPasswordForSpecialSymbols,
                SignUpValidator::checkPasswordForNumbers,
            )
        val passwordErrorMassages: MutableList<String> =
            mutableListOf(
                getString(R.string.less_8_symbols_pswd_error, MIN_NUM_OF_CHARS_IN_PASSWORD),
                getString(R.string.capital_letter_error),
                getString(R.string.lowercase_letter_error),
                getString(R.string.special_symbol_error),
                getString(R.string.numbers_error),
            )

        for ((index, check) in passwordChecks.withIndex()) {
            if (!check.invoke(inputPassword)) {
                errorMassage = passwordErrorMassages[index]
                tilPassword.helperText = errorMassage
                return errorMassage
            }
        }
        tilPassword.helperText = null
        return errorMassage
    }

    private fun saveUserInfo(eMail: String, password: String) {
        val editor = sharedPref.edit()
        editor.putString(EMAIL_KEY, eMail)
        editor.putString(PASSWORD_KEY, password)
        editor.apply()
    }
}
