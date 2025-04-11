package com.example.androidcourseshpp

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidcourseshpp.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var sharedPref: SharedPreferences

    private companion object {
        const val SPECIAL_SYMBOLS = " !#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\""
        const val MIN_NUM_OF_CHARS_IN_PSWD = 8
        const val USER_INFO_STORE = "userInfo"
        const val EMAIL_KEY = "EMAIL_KEY"
        const val PSWD_KEY = "PSWD_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences(USER_INFO_STORE, MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(binding.auth) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        moveToExistingAccount()
        setListeners()
    }

    private fun moveToExistingAccount() {
        val savedName = sharedPref.getString(EMAIL_KEY, "").toString()
        if (savedName != "") {
            moveToMainActivity(savedName)
        }
    }

    private fun setListeners() {
        with(binding) {

            binding.btRegister.setOnClickListener {
                val isEMailCorrect = checkEMailInput()
                val isPasswordCorrect = checkPasswordInput()

                if (isEMailCorrect && isPasswordCorrect) {
                    if (cbRememberMe.isChecked) {
                        saveUserInfo(etEMail.text.toString(), etPassword.text.toString())
                    }
                    moveToMainActivity(etEMail.text.toString())
                }
            }
        }
    }

    private fun moveToMainActivity(userEMail: String) {
        val intent = Intent(this@AuthActivity, MainActivity::class.java)

        intent.putExtra(EMAIL_KEY, userEMail)

        val options = ActivityOptions.makeCustomAnimation(
            this@AuthActivity, R.anim.my_profile_fade_in, R.anim.sing_up_fade_out
        )

        startActivity(intent, options.toBundle())
        finish()
    }

    private fun checkEMailInput(): Boolean {
        with(binding) {

            val inputEMail: String = etEMail.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(inputEMail).matches()) {
                tilEMail.helperText = getString(R.string.email_error)
                return false
            }

            tilEMail.helperText = null
        }
        return true
    }

    private fun checkPasswordInput(): Boolean {
        with(binding) {

            val inputPassword: String = etPassword.text.toString()
            val passwordChecks: MutableList<(s: String) -> String> = mutableListOf(
                ::checkForNumOfLetters,
                ::checkForUpperCase,
                ::checkForLowerCase,
                ::checkForSpecialSymbols,
                ::checkForNumbers,
            )

            passwordChecks.forEach { check ->
                val helpMess = check.invoke(inputPassword)

                if (helpMess.isNotEmpty()) {
                    tilPassword.helperText = helpMess
                    return false
                }
            }

            tilPassword.helperText = null
            return true
        }
    }

    private fun checkForNumOfLetters(inPswd: String): String {
        return if (inPswd.length < MIN_NUM_OF_CHARS_IN_PSWD)
            getString(R.string.less_8_symbols_pswd_error, MIN_NUM_OF_CHARS_IN_PSWD) else ""
    }

    private fun checkForUpperCase(inPswd: String): String {
        return if (inPswd.any { it.isUpperCase() }) ""
        else getString(R.string.capital_letter_error)
    }

    private fun checkForLowerCase(inPswd: String): String {
        return if (inPswd.any { it.isLowerCase() }) ""
        else getString(R.string.lowercase_letter_error)
    }

    private fun checkForSpecialSymbols(inPswd: String): String {
        return if (inPswd.any { SPECIAL_SYMBOLS.contains(it) }) ""
        else getString(R.string.special_symbol_error)
    }

    private fun checkForNumbers(inPswd: String): String {
        return if (inPswd.any { it.isDigit() }) ""
        else getString(R.string.numbers_error)
    }

    private fun saveUserInfo(eMail: String, pswd: String) {
        val editor = sharedPref.edit()
        editor.putString(EMAIL_KEY, eMail)
        editor.putString(PSWD_KEY, pswd)
        editor.apply()
    }
}
