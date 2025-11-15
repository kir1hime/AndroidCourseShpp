package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.databinding.FragmentSignUpExtendedBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL
import com.example.androidcourseshpp.ui.screens.auth.AuthFragment
import com.example.androidcourseshpp.ui.screens.auth.signupextended.chooseprofilephotodialog.ChooseProfilePhotoDialog
import com.example.androidcourseshpp.ui.screens.auth.signupextended.chooseprofilephotodialog.ChooseProfilePhotoDialog.Companion.PHOTO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpExtendedFragment : AuthFragment() {

    private lateinit var binding: FragmentSignUpExtendedBinding
    private val viewModel by viewModels<SignUpExtendedViewModel>()

    private val args: SignUpExtendedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpExtendedBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        setChooseProfilePhotoDialogListener()
        formatMobilePhoneInput(binding.editTextMobilePhone)
    }

    private fun setListeners() = with(binding) {
        buttonForward.setOnClickListener {
            rememberUserName()
            val inputUserName = editTextUserName.text.toString()
            val inputMobilePhone = editTextMobilePhone.text.toString()

            viewModel.setEvent(
                SignUpExtendedContract.Event.OnForwardButtonClicked(
                    inputUserName,
                    inputMobilePhone,
                    args.SignUpExtendedEntity.userServerId
                )
            )
        }
        buttonCancel.setOnClickListener {
            viewModel.setEvent(SignUpExtendedContract.Event.OnCancelButtonClicked)
        }
        imageButtonAddProfilePhoto.setOnClickListener {
            viewModel.setEvent(SignUpExtendedContract.Event.OnAddProfilePhotoImageViewClicked)
        }
    }


    private fun rememberUserName() = with(binding) {
        args.SignUpExtendedEntity.apply {
            if (toRememberUser) {
                viewModel.setEvent(SignUpExtendedContract.Event.SaveUserName(userServerId))
            }
        }
    }

    private fun setObservers() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is SignUpExtendedContract.Effect.NavigateToMyProfileScreen ->{
                    Log.d("myTag", "aldfj")
                    moveToMyProfileScreen(
                    effect.userInfoEntity
                )}

                is SignUpExtendedContract.Effect.NavigateToPreviousScreen -> findNavController().navigateUp()

                is SignUpExtendedContract.Effect.NavigateToChooseProfilePhotoDialog -> moveToChooseProfilePhotoDialog()

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

    private fun setChooseProfilePhotoDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            ChooseProfilePhotoDialog.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, data ->
            val newProfilePhoto = data.getString(PHOTO) ?: ""
            binding.circleImageViewProfilePhoto.loadImageFromURL(requireContext(), newProfilePhoto)
        }
    }


    private fun moveToChooseProfilePhotoDialog() {
        val direction =
            SignUpExtendedFragmentDirections.actionSignUpExtendedFragmentToChooseProfilePhotoDialog()
        findNavController().navigate(direction)
    }
}