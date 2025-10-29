package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlin.time.Duration

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
        formatMobilePhoneInput()
    }

    private fun setListeners() = with(binding) {
        buttonForward.setOnClickListener {
            val inputUserName = editTextUserName.text.toString()
            val inputMobilePhone = editTextMobilePhone.text.toString()
            viewModel.setEvent(
                SignUpExtendedContract.Event.OnForwardButtonClicked(
                    inputUserName,
                    inputMobilePhone,
                    args.email,
                    args.serverUserId
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

    private fun setObservers() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is SignUpExtendedContract.Effect.NavigateToMyProfileScreen -> moveToMyProfileScreen(
                    editTextUserName.text.toString()
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
            setLoadingState(state.isProgressBarShowed)
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

    private fun formatMobilePhoneInput() = with(binding) {
        editTextMobilePhone.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(
                inputText: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                inputText?.let {

                    if (BRACKET_POSITIONS.keys.contains(inputText.length) && count > 0 && before == 0) {
                        updateMobilePhoneInput(BRACKET_POSITIONS[inputText.length])
                    }
                    if (HYPHEN_POSITIONS.contains(inputText.length) && count > 0 && before == 0) {
                        updateMobilePhoneInput("-")

                    }
                }
            }
        })
    }

    private fun setLoadingState(isLoaded: Boolean) = with(binding) {
        val isEnabled = !isLoaded

        editTextUserName.apply {
            isFocusable = isEnabled
            isFocusableInTouchMode = isEnabled
        }
        editTextMobilePhone.apply {
            isFocusable = isEnabled
            isFocusableInTouchMode = isEnabled
        }
        imageButtonAddProfilePhoto.isClickable = isEnabled
    }

    private fun updateMobilePhoneInput(sign: String?) = with(binding) {
        val currentText = editTextMobilePhone.text.toString()
        val newInputText = StringBuilder(currentText)
        newInputText.insert(currentText.length - 1, sign)
        editTextMobilePhone.setText(newInputText)
        editTextMobilePhone.setSelection(editTextMobilePhone.length())
    }

    companion object {
        private val BRACKET_POSITIONS = mapOf(1 to "(", 5 to ")-")
        private val HYPHEN_POSITIONS = listOf(6, 10, 13)
    }

}