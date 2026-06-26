package com.example.androidcourseshpp.ui.screens.auth.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.databinding.FragmentSignUpBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.auth.signup.model.SignUpModel
import com.example.androidcourseshpp.ui.utils.MIN_NUM_OF_CHARS_IN_PASSWORD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserves()
        setListeners()
    }

    private fun setObserves() = with(binding) {
        collectFlowWithLifecycle(viewModel.effect) { effect ->
            when (effect) {
                is SignUpContract.Effect.NavigateToSignUpExtended -> moveToSignUpExtended(effect.signUpUserInfo)
                is SignUpContract.Effect.ShowToast -> makeToast(effect.toastMessageResId)
            }
        }

        collectFlowWithLifecycle(viewModel.state) { state ->
            textInputLayoutPassword.helperText =
                getString(state.passwordHelperTextResId, MIN_NUM_OF_CHARS_IN_PASSWORD)
            textInputLayoutEMail.helperText = getString(state.eMailHelperTextResId)
        }
    }

    private fun setListeners() = with(binding) {
        buttonRegister.setOnClickListener {
            onRegisterButtonClick()
        }

        textViewSignIn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onRegisterButtonClick() = with(binding) {
        val inputEMail = editTextEMail.text.toString()
        val inputPassword = editTextPassword.text.toString()

        viewModel.setEvent(
            SignUpContract.Event.OnResisterButtonClicked(
                inputEMail,
                inputPassword,
                comboBoxRememberMe.isChecked
            )
        )
    }

    private fun moveToSignUpExtended(signUpUserInfo: SignUpModel) {
        val direction =
            SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment(signUpUserInfo)
        findNavController().navigate(direction)
    }
}