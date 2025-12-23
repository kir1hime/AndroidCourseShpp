package com.example.androidcourseshpp.ui.screens.auth.signin

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.data.MIN_NUM_OF_CHARS_IN_PASSWORD
import com.example.androidcourseshpp.databinding.FragmentSignInBinding
import com.example.androidcourseshpp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>
    (FragmentSignInBinding::inflate) {
    private val viewModel by viewModels<SignInViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }

    override fun setObservers() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is SignInContract.Effect.NavigateToSingUpScreen -> moveToSignUpScreen()
                is SignInContract.Effect.NavigateToMyProfileScreen -> moveToMyProfileScreen(effect.userServerId)
                is SignInContract.Effect.ShowToast -> makeToast(effect.toastMessageResId)
            }
        }

        collectFlow(viewModel.state) { state ->
            textInputLayoutPassword.helperText =
                getString(state.passwordHelperTextResId, MIN_NUM_OF_CHARS_IN_PASSWORD)

            textInputLayoutEMail.helperText = getString(state.eMailHelperTextResId)

            progressBarRequest.isVisible = state.isProgressBarShowed
            setLoadingState(state.isProgressBarShowed, binding)
        }
    }

    override fun setListeners() = with(binding) {
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

    private fun moveToSignUpScreen() {
        val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        findNavController().navigate(direction)
    }
}