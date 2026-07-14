package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.os.Bundle

import android.view.View
import android.widget.Toast
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

    private fun setListeners() = with(binding) {
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

    private fun setObservers() = with(binding) {
        var toast: Toast? = null
        collectFlowWithLifecycle(viewModel.effect) { effect ->
            when (effect) {

                is SignUpExtendedContract.Effect.NavigateToPreviousScreen -> findNavController().navigateUp()
                is SignUpExtendedContract.Effect.NavigateToChooseProfilePhotoDialog -> {
                    val direction =
                        SignUpExtendedFragmentDirections.actionSignUpExtendedFragmentToChooseProfilePhotoDialog()
                    findNavController().navigate(direction)
                }

                is SignUpExtendedContract.Effect.NavigateToUserProfileScreen -> moveToUserProfileScreen(
                    effect.userInfo
                )

                is SignUpExtendedContract.Effect.ShowToast -> {
                    toast?.cancel()
                    toast = makeToast(effect.toastMessageResId)
                    toast.show()
                }
            }
        }

        collectFlowWithLifecycle(viewModel.state) { state ->
            textInputLayoutUserName.helperText = getString(state.userNameHelperResId)
            textInputLayoutMobilePhone.helperText = getString(state.mobilePhoneHelperResId)
            progressBarRequest.isVisible = state.isProgressBarShowed
            setLoadingState(state.isProgressBarShowed)
        }
    }

    private fun setLoadingState(isLoading: Boolean) = with(binding) {
        val isEnabled = !isLoading
        enableEditText(editTextUserName)
        enableEditText(editTextMobilePhone)
        imageButtonAddProfilePhoto.isClickable = isEnabled
    }
}