package com.example.androidcourseshpp.ui.screens.auth.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.databinding.FragmentSignInBinding
import com.example.androidcourseshpp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>
    (FragmentSignInBinding::inflate) {
    private val viewModel by viewModels<SignInViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }

    private fun setObservers() = with(binding) {
        var toast: Toast? = null
        collectFlowWithLifecycle(viewModel.effect) { effect ->
            when (effect) {
                is SignInContract.Effect.NavigateToSignUpScreen -> moveToSignUpScreen()
                is SignInContract.Effect.NavigateToUserProfileScreen -> moveToUserProfileScreen(
                    effect.userInfo
                )

                is SignInContract.Effect.ShowToast -> {
                    toast?.cancel()
                    toast = makeToast(effect.toastMessageResId)
                    toast.show()
                }
            }
        }

        collectFlowWithLifecycle(viewModel.state) { state ->
            textInputLayoutPassword.helperText = getString(state.passwordHelperTextResId)

            textInputLayoutEMail.helperText = getString(state.eMailHelperTextResId)

            progressBarRequest.isVisible = state.isProgressBarShowed
            setLoadingState(state.isProgressBarShowed)
        }
    }

    private fun setListeners() = with(binding) {
        buttonLogin.setOnClickListener {
            viewModel.setEvent(
                SignInContract.Event.OnLoginButtonClicked(
                    editTextEMail.text.toString(),
                    editTextPassword.text.toString(),
                    comboBoxRememberMe.isChecked
                )
            )
        }
        textViewSignUp.setOnClickListener {
            viewModel.setEvent(SignInContract.Event.OnSignUpLabelClicked)
        }
    }

    private fun setLoadingState(isLoading: Boolean) = with(binding) {
        val isEnabled = !isLoading

        enableEditText(editTextEMail)
        enableEditText(editTextPassword)
        comboBoxRememberMe.isClickable = isEnabled
    }

    private fun moveToSignUpScreen() {
        val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        findNavController().navigate(direction)
    }
}