package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.androidcourseshpp.databinding.FragmentSignUpExtendedBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL
import com.example.androidcourseshpp.ui.screens.auth.AuthFragment
import com.example.androidcourseshpp.ui.screens.auth.choosephotodialog.ChooseProfileDialogViewModel
import com.example.androidcourseshpp.ui.screens.auth.choosephotodialog.ChooseProfilePhotoDialog
import com.example.androidcourseshpp.ui.screens.auth.choosephotodialog.ChooseProfilePhotoDialog.Companion.PHOTO

class SignUpExtendedFragment : AuthFragment() {

    private lateinit var binding: FragmentSignUpExtendedBinding
    private val args by navArgs<SignUpExtendedFragmentArgs>()

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
        setChooseProfilePhotoDialogListener()
        formatMobilePhoneInput()
    }

    private fun setListeners() = with(binding) {
        buttonForward.setOnClickListener {
            moveToMyProfileScreen(args.userEmail)
        }
        buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        imageButtonAddProfilePhoto.setOnClickListener {
            val direction =
                SignUpExtendedFragmentDirections.actionSignUpExtendedFragmentToChooseProfilePhotoDialog()

            findNavController().navigate(direction)
        }

    }

    private fun setChooseProfilePhotoDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            ChooseProfilePhotoDialog.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, data ->
            Log.d("myTag", data.getString(PHOTO).toString())
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