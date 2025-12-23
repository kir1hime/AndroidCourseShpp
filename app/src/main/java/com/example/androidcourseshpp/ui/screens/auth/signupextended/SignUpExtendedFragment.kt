package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.os.Bundle

import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.databinding.FragmentSignUpExtendedBinding
import com.example.androidcourseshpp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpExtendedFragment : BaseFragment<FragmentSignUpExtendedBinding>(
    FragmentSignUpExtendedBinding::inflate
) {
    private val viewModel by viewModels<SignUpExtendedViewModel>()

    private val args: SignUpExtendedFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        setChooseProfilePhotoDialogResultListener(binding.circleImageViewProfilePhoto) {}
        formatMobilePhoneInput(binding.editTextMobilePhone)
    }

    override fun setListeners() = with(binding) {
        buttonForward.setOnClickListener { onForwardButtonClick() }

        buttonCancel.setOnClickListener {
            viewModel.setEvent(SignUpExtendedContract.Event.OnCancelButtonClicked)
        }

        imageButtonAddProfilePhoto.setOnClickListener {
            viewModel.setEvent(SignUpExtendedContract.Event.OnAddProfilePhotoImageViewClicked)
        }
    }

    private fun onForwardButtonClick() = with(binding) {
        val inputUserName = editTextUserName.text.toString()
        val inputMobilePhone = editTextMobilePhone.text.toString()

        viewModel.setEvent(
            SignUpExtendedContract.Event.OnForwardButtonClicked(
                inputUserName,
                inputMobilePhone,
                args.signUpInfo,
                circleImageViewProfilePhoto.drawable.toBitmap()
            )
        )
    }

    override fun setObservers() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is SignUpExtendedContract.Effect.NavigateToMyProfileScreen -> moveToMyProfileScreen(
                    effect.userServerId
                )

                is SignUpExtendedContract.Effect.NavigateToPreviousScreen -> findNavController().navigateUp()
                is SignUpExtendedContract.Effect.NavigateToChooseProfilePhotoDialog -> {
                    val direction =
                        SignUpExtendedFragmentDirections.actionSignUpExtendedFragmentToChooseProfilePhotoDialog()
                    findNavController().navigate(direction)
                }

                is SignUpExtendedContract.Effect.ShowToast -> makeToast(effect.toastMessageResId)
            }
        }

        collectFlow(viewModel.state) { state ->
            textInputLayoutUserName.helperText = getString(state.userNameHelperResId)
            textInputLayoutMobilePhone.helperText = getString(state.mobilePhoneHelperResId)
            progressBarRequest.isVisible = state.isProgressBarShowed
            setLoadingState(state.isProgressBarShowed, binding)
        }
    }
}