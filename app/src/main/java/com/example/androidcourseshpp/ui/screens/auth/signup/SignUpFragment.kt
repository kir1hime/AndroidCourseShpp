package com.example.androidcourseshpp.ui.screens.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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

        setObserves()
        setListeners()
    }

    private fun setObserves() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is SignUpContract.Effect.NavigateToSignUpExtendedScreen -> moveToSignUpExtendedScreen(
                    effect.serverUserId,
                    effect.email,
                    effect.password,
                    effect.toRememberUser
                )

                is SignUpContract.Effect.ShowToast -> makeToast(effect.toastMessageResId)
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

    private fun moveToSignUpExtendedScreen(
        serverUserId: Long,
        email: String,
        password: String,
        toRememberUser: Boolean
    ) {
        val direction =
            SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment(
                email,
                password,
                serverUserId,
                toRememberUser
            )
        findNavController().navigate(direction)
    }
}