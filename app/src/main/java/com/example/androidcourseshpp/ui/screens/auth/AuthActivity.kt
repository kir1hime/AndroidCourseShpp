package com.example.androidcourseshpp.ui.screens.auth

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.PasswordErrorMessagesContainer
import com.example.androidcourseshpp.data.SignUpValidator
import com.example.androidcourseshpp.data.dataProvider.EMAIL_KEY
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import com.example.androidcourseshpp.databinding.ActivityAuthBinding
import com.example.androidcourseshpp.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptUserInterface(binding.root)

        val savedEmail = viewModel.savedEMail.value
        if (savedEmail != "") {
            moveToMyProfileScreen(savedEmail)
        }

        setListeners()
    }


    private fun setListeners() = with(binding) {
        btRegister.setOnClickListener {

            val inputEMail = etEMail.text.toString()
            val inputPassword = etPassword.text.toString()

            val isInputEMailCorrect = SignUpValidator.isEMailCorrect(inputEMail)
            val isInputPasswordCorrect = SignUpValidator.isPasswordCorrect(inputPassword)

            if (isInputEMailCorrect && isInputPasswordCorrect) {
                tilPassword.helperText = ""
                tilEMail.helperText = ""

                if (cbRememberMe.isChecked) {

                    viewModel.saveUserInfo(
                        etEMail.text.toString(),
                        etPassword.text.toString()
                    )
                }

                moveToMyProfileScreen(etEMail.text.toString())

            } else {
                tilEMail.helperText =
                    if (!isInputEMailCorrect) {
                        getString(R.string.email_error)
                    } else ""
                tilPassword.helperText =
                    if (!isInputPasswordCorrect) {
                        viewModel.definePasswordErrorMessage(inputPassword, PasswordErrorMessagesContainer(this@AuthActivity).getMessages())
                    } else ""
            }
        }
    }

    private fun moveToMyProfileScreen(userEMail: String) {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra(EMAIL_KEY, userEMail)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_from_right_to_left,
            R.anim.slide_out_from_right_to_left
        )

        startActivity(intent, options.toBundle())
        finish()
    }
}
