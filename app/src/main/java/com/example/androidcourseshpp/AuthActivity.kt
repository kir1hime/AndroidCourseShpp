package com.example.androidcourseshpp

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidcourseshpp.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val SPECIAL_SYMBOLS = " !#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\""
    private val NUMBERS = "0123456789"
    private val MIN_NUMBER_OF_CHARS_IN_PASSWORD = 8
    private val EMAIL_KEY = "EMAIL_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.auth)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btRegister.setOnClickListener {
            /*val isEMailCorrect = checkEMailInput()
            val isPasswordCorrect = checkPasswordInput()
            if (isEMailCorrect && isPasswordCorrect) {*/

            val intent = Intent(this@AuthActivity, MainActivity::class.java)

            intent.putExtra(EMAIL_KEY, binding.etEMail.text.toString())

            val options = ActivityOptions.makeCustomAnimation(
                this, R.anim.fade_in, R.anim.fade_out
            )
            startActivity(intent, options.toBundle())
            /* }*/
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
            if (inputPassword.length < MIN_NUMBER_OF_CHARS_IN_PASSWORD) {
                tilPassword.helperText = getString(R.string.less_8_symbols_pswd_error)
                return false
            } else if (!checkForUpperCase(inputPassword)) {
                tilPassword.helperText = getString(R.string.capital_letter_error)
                return false
            } else if (!checkForLowerCase(inputPassword)) {
                tilPassword.helperText = getString(R.string.lowercase_letter_error)
                return false
            } else if (!checkForSpecialSymbols(inputPassword)) {
                tilPassword.helperText = getString(R.string.special_symbol_error)
                return false
            } else if (!checkForNumbers(inputPassword)) {
                tilPassword.helperText = getString(R.string.numbers_error)
                return false
            } else {
                tilPassword.helperText = null
            }

        }

        return true
    }

    private fun checkForUpperCase(inPswd: String): Boolean {
        for (ch in inPswd) {
            if (ch.isUpperCase()) {
                return true
            }
        }
        return false
    }

    private fun checkForLowerCase(inPswd: String): Boolean {
        for (ch in inPswd) {
            if (ch.isLowerCase()) {
                return true
            }
        }
        return false
    }

    private fun checkForSpecialSymbols(inPswd: String): Boolean {
        for (ch in inPswd) {
            if (checkForSpecialSymbol(ch)) {
                return true
            }
        }
        return false
    }

    private fun checkForSpecialSymbol(symbol: Char): Boolean {
        for (specialSymbol in SPECIAL_SYMBOLS) {
            if (symbol == specialSymbol) {
                return true
            }
        }
        return false
    }

    private fun checkForNumbers(inPswd: String): Boolean {
        for (ch in inPswd) {
            if (checkForNumber(ch)) {
                return true
            }
        }
        return false
    }

    private fun checkForNumber(symbol: Char): Boolean {
        for (number in NUMBERS) {
            if (symbol == number) {
                return true
            }
        }
        return false
    }

}