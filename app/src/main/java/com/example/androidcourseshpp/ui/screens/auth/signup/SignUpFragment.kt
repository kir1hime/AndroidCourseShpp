package com.example.androidcourseshpp.ui.screens.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.data.MIN_NUM_OF_CHARS_IN_PASSWORD
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

        val savedEmail = viewModel.savedEmail
        if (savedEmail != "") {
            moveToMyProfileScreen("")
        }

        setObserves()
        setListeners()
    }

    private fun setObserves() = with(binding) {
        collectFlow(viewModel.effect) {
            moveToSignUpExtended(editTextEMail.text.toString())
        }
        collectFlow(viewModel.state) { state ->

            textInputLayoutPassword.helperText =
                getString(state.passwordHelperTextResId, MIN_NUM_OF_CHARS_IN_PASSWORD)

            textInputLayoutEMail.helperText = getString(state.eMailHelperTextResId)

        }
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener {
            //moveToSignUpExtended(binding.editTextEMail.text.toString())
            onRegisterButtonClick()
        }
    }

    private fun onRegisterButtonClick() = with(binding) {
        val inputEMail = editTextEMail.text.toString()
        val inputPassword = editTextPassword.text.toString()

        viewModel.processInputData(inputEMail, inputPassword, comboBoxRememberMe.isChecked)
    }

    private fun moveToSignUpExtended(userEmail: String) {
        val direction =
            SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment(userEmail)
        findNavController().navigate(direction)
    }


}