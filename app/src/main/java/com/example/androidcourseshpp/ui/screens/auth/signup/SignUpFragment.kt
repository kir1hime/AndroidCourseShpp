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

        val savedEmail = viewModel.getUserEMail()
        if (savedEmail != "") {
            moveToMyProfileScreen("")
        }

        setListeners()
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener {
            onRegisterButtonClick()
        }
    }

    private fun onRegisterButtonClick() = with(binding) {
        val inputEMail = editTextEMail.text.toString()
        val inputPassword = editTextPassword.text.toString()

        viewModel.processInputData(inputEMail, inputPassword, comboBoxRememberMe.isChecked)

        val state = viewModel.state

        textInputLayoutPassword.helperText =
            getString(state.passwordHelperTextResId, MIN_NUM_OF_CHARS_IN_PASSWORD)

        textInputLayoutEMail.helperText = getString(state.eMailHelperTextResId)

        if (state.isPasswordCorrect && state.isEmailCorrect) {
            moveToSignUpExtended(editTextEMail.text.toString())
        }

    }
    private fun moveToSignUpExtended(userEmail: String) {
        val direction =
            SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment(userEmail)
        findNavController().navigate(direction)
    }


}