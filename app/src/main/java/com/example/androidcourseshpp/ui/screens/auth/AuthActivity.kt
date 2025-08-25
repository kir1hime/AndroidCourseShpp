package com.example.androidcourseshpp.ui.screens.auth

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.MIN_NUM_OF_CHARS_IN_PASSWORD
import com.example.androidcourseshpp.data.SignUpValidator
import com.example.androidcourseshpp.ui.screens.MainActivity
import com.example.androidcourseshpp.databinding.ActivityAuthBinding
import com.example.androidcourseshpp.ui.extensions.adaptUserInterface
import com.example.androidcourseshpp.ui.utils.factory

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel by viewModels<SignUpViewModel> { factory() }

    private val passwordErrorMessages  by lazy {
        listOf(
            getString(R.string.less_8_symbols_pswd_error, MIN_NUM_OF_CHARS_IN_PASSWORD),
            getString(R.string.capital_letter_error),
            getString(R.string.lowercase_letter_error),
            getString(R.string.special_symbol_error),
            getString(R.string.numbers_error),
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptUserInterface(binding.root)

        if (viewModel.savedEMail.value != "") {
            moveToMyProfileScreen()
        }

        setListeners()
    }


    private fun setListeners() = with(binding) {
        buttonRegister.setOnClickListener {

            val inputEMail = editTextEMail.text.toString()
            val inputPassword = editTextPassword.text.toString()

            val isInputEMailCorrect = SignUpValidator.isEMailCorrect(inputEMail)
            val isInputPasswordCorrect = SignUpValidator.isPasswordCorrect(inputPassword)

            if (isInputEMailCorrect && isInputPasswordCorrect) {
                textInputLayoutPassword.helperText = ""
                textInputLayoutEMail.helperText = ""

                if (checkBoxRememberMe.isChecked) {

                    viewModel.saveUserInfo(
                        editTextEMail.text.toString(),
                        editTextPassword.text.toString()
                    )
                }

                moveToMyProfileScreen()

            } else {
                textInputLayoutEMail.helperText =
                    if (!isInputEMailCorrect) {
                        getString(R.string.email_error)
                    } else ""
                textInputLayoutPassword.helperText =
                    if (!isInputPasswordCorrect) {
                        viewModel.definePasswordErrorMessage(inputPassword, passwordErrorMessages)
                    } else ""
            }
        }
    }

    private fun moveToMyProfileScreen() {
        val intent = Intent(this, MainActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_from_right_to_left,
            R.anim.slide_out_from_right_to_left
        )

        startActivity(intent, options.toBundle())
        finish()
    }
}
