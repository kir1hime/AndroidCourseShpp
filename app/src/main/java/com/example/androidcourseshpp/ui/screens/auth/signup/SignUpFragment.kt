package com.example.androidcourseshpp.ui.screens.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.MIN_NUM_OF_CHARS_IN_PASSWORD
import com.example.androidcourseshpp.data.PasswordErrorMessagesContainer
import com.example.androidcourseshpp.data.SignUpValidator
import com.example.androidcourseshpp.databinding.FragmentSignUpBinding
import com.example.androidcourseshpp.ui.screens.auth.AuthFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : AuthFragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val savedEmail = viewModel.savedEMail.value
        if (savedEmail != "") {
            moveToMyProfileScreen("")
        }

        setListeners()
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener {
            onRegisterButtonClickListener()
        }
    }

    private fun onRegisterButtonClickListener() = with(binding) {
        val inputEMail = editTextEMail.text.toString()
        val inputPassword = editTextPassword.text.toString()

        val isInputEMailCorrect = SignUpValidator.isEMailCorrect(inputEMail)
        val isInputPasswordCorrect = SignUpValidator.isPasswordCorrect(inputPassword)

        if (isInputEMailCorrect && isInputPasswordCorrect) {
            textInputLayoutPassword.helperText = ""
            textInputLayoutEMail.helperText = ""

            if (comboBoxRememberMe.isChecked) {
                saveUserInfo()
            }
            moveToSignUpExtended(editTextEMail.text.toString())

        } else {
            setPasswordErrorMessage(isInputPasswordCorrect, inputPassword)
            setEMailErrorMessage(isInputEMailCorrect)
        }
    }

    private fun saveUserInfo() = with(binding) {
        viewModel.saveUserInfo(
            editTextEMail.text.toString(),
            editTextPassword.text.toString()
        )
    }

    private fun setPasswordErrorMessage(isInputPasswordCorrect: Boolean, inputPassword: String) {
        binding.textInputLayoutPassword.helperText =
            if (!isInputPasswordCorrect) {
                viewModel.definePasswordErrorMessage(
                    inputPassword,
                    PasswordErrorMessagesContainer.getMessageResourceIds()
                        .map { getString(it, MIN_NUM_OF_CHARS_IN_PASSWORD) })
            } else ""
    }

    private fun setEMailErrorMessage(isInputEMailCorrect: Boolean) {
        binding.textInputLayoutEMail.helperText =
            if (!isInputEMailCorrect) {
                getString(R.string.email_error)
            } else ""
    }


    private fun moveToSignUpExtended(userEmail: String) {
        val direction = SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment(userEmail)
        findNavController().navigate(direction)
    }


}