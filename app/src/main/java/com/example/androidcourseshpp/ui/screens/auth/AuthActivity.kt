package com.example.androidcourseshpp.ui.screens.auth

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.SignUpValidator
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import com.example.androidcourseshpp.databinding.ActivityAuthBinding
import com.example.androidcourseshpp.ui.extensions.adaptUserInterface
import com.example.androidcourseshpp.ui.utils.factory

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel by viewModels<SignUpViewModel> {factory()}

    private companion object {
        const val EMAIL_KEY = "userEMail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptUserInterface(binding.root)

        val userEMail = viewModel.getUserEMail(EMAIL_KEY)

        if (viewModel.isExistingAccount(EMAIL_KEY)) {
            moveToMyProfileScreen(userEMail)
        }

        setListeners()
    }


    private fun setListeners() = with(binding) {
        btRegister.setOnClickListener {

            val inputEMail = etEMail.text.toString()
            val inputPassword = etPassword.text.toString()

            if (SignUpValidator.isEMailCorrect(inputEMail).also {
                    if (!it) tilEMail.helperText =
                        getString(R.string.email_error) else tilEMail.helperText = ""
                }
                and
                SignUpValidator.isPasswordCorrect(inputPassword).also {
                    if (!it) tilPassword.helperText =
                        viewModel.definePasswordErrorMessage(inputPassword)
                    else tilPassword.helperText = ""
                }
            ) {

                if (cbRememberMe.isChecked) {

                    viewModel.saveUserInfo(
                        etEMail.text.toString(),
                        etPassword.text.toString()
                    )
                }

                moveToMyProfileScreen(etEMail.text.toString())
            }
        }
    }

    private fun moveToMyProfileScreen(userEMail: String) {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra(EMAIL_KEY, userEMail)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.my_profile_fade_in_from_right_to_left,
            R.anim.sing_up_fade_out_from_right_to_left
        )

        startActivity(intent, options.toBundle())
        finish()
    }
}
