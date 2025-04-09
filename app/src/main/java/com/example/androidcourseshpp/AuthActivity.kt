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
        const val NUMBERS = "0123456789"
        const val MIN_NUM_OF_CHARS_IN_PSWD = 8
        const val EMAIL_KEY = "EMAIL_KEY"
        const val PSWD_KEY = "PASSWORD_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.auth)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            val userInfo = getUserInfo()
            etEMail.setText(userInfo[0])
            etPassword.setText(userInfo[1])

            binding.btRegister.setOnClickListener {
                val isEMailCorrect = checkEMailInput()
                val isPasswordCorrect = checkPasswordInput()
                if (isEMailCorrect && isPasswordCorrect) {

                    val intent = Intent(this@AuthActivity, MainActivity::class.java)

                    intent.putExtra(EMAIL_KEY, binding.etEMail.text.toString())

                    val options = ActivityOptions.makeCustomAnimation(
                        this@AuthActivity, R.anim.fade_in, R.anim.fade_out
                    )
                    startActivity(intent, options.toBundle())
                }
            }

            cbRememberMe.setOnClickListener {
                saveUserInfo(etEMail.text.toString(), etPassword.text.toString())
            }
        }

    }

    private fun checkEMailInput(): Boolean {
        with(binding) {

            val inputEMail: String = etEMail.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(inputEMail).matches()) {
                tilEMail.helperText = getString(R.string.email_error)
                return false
            } else {
                tilEMail.helperText = null
            }
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

            for (check in passwordChecks) {
                val helpMess = check.invoke(inputPassword)

                if (helpMess == "") {
                    continue
                }

                tilPassword.helperText = helpMess
                return false
            }

        }

        return true
    }

    private fun checkForNumOfLetters(inPswd: String): String {
        if (inPswd.length < MIN_NUM_OF_CHARS_IN_PSWD) {
            return getString(R.string.less_8_symbols_pswd_error)
        }
        return ""
    }

    private fun checkForUpperCase(inPswd: String): String {
        for (ch in inPswd) {
            if (ch.isUpperCase()) {
                return ""
            }
        }
        return getString(R.string.capital_letter_error)
    }

    private fun checkForLowerCase(inPswd: String): String {
        for (ch in inPswd) {
            if (ch.isLowerCase()) {
                return ""
            }
        }
        return  getString(R.string.lowercase_letter_error)
    }

    private fun checkForSpecialSymbols(inPswd: String): String  {
        for (ch in inPswd) {
            if (checkForSpecialSymbol(ch)) {
                return ""
            }
        }
        return getString(R.string.special_symbol_error)
    }

    private fun checkForSpecialSymbol(symbol: Char): Boolean {
        for (specialSymbol in SPECIAL_SYMBOLS) {
            if (symbol == specialSymbol) {
                return true
            }
        }
        return false
    }

    private fun checkForNumbers(inPswd: String): String  {
        for (ch in inPswd) {
            if (checkForNumber(ch)) {
                return ""
            }
        }
        return getString(R.string.numbers_error)
    }

    private fun checkForNumber(symbol: Char): Boolean {
        for (number in NUMBERS) {
            if (symbol == number) {
                return true
            }
        }
        return false
    }

    private fun saveUserInfo(eMail: String, pswd: String) {
        val editor = sharedPref.edit()
        editor.putString(EMAIL_KEY, eMail)
        editor.putString(PSWD_KEY, pswd)
        editor.apply()
    }

    private fun getUserInfo(): Array<String> {
        val userInfo: Array<String> = Array(2) { "" }
        userInfo[0] = sharedPref.getString(EMAIL_KEY, "").toString()
        userInfo[1] = sharedPref.getString(PSWD_KEY, "").toString()

        return userInfo
    }

}