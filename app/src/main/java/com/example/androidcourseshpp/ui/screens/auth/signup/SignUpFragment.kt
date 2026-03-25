package com.example.androidcourseshpp.ui.screens.auth.signup

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.data.MIN_NUM_OF_CHARS_IN_PASSWORD
import com.example.androidcourseshpp.databinding.FragmentSignUpBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.SignUpUserInfo
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
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is SignUpContract.Effect.NavigateToSignUpExtended -> moveToSignUpExtended(
                    effect.signUpUserInfo
                )

                is SignUpContract.Effect.ShowToast -> makeToast(effect.toastMessageResId)
                is SignUpContract.Effect.NavigateToMyProfileScreen -> moveToMyProfileScreen(effect.userInfo)
            }
        }

        collectFlow(viewModel.state) { state ->
            textInputLayoutPassword.helperText =
                getString(state.passwordHelperTextResId, MIN_NUM_OF_CHARS_IN_PASSWORD)

            textInputLayoutEMail.helperText = getString(state.eMailHelperTextResId)

            progressBarRequest.isVisible = state.isProgressBarShowed
            setLoadingState(state.isProgressBarShowed)
        }
    }

    private fun setListeners() = with(binding) {
        buttonRegister.setOnClickListener {
            onRegisterButtonClick()
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

    private fun moveToSignUpExtended(signUpUserInfo: SignUpUserInfo) {
        val direction =
            SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment(signUpUserInfo)
        findNavController().navigate(direction)
    }

    private fun setLoadingState(isLoaded: Boolean) = with(binding) {
        val isEnabled = !isLoaded

        editTextEMail.apply {
            isFocusable = isEnabled
            isFocusableInTouchMode = isEnabled
        }
        editTextPassword.apply {
            isFocusable = isEnabled
            isFocusableInTouchMode = isEnabled
        }
        comboBoxRememberMe.isClickable = isEnabled

    }
}