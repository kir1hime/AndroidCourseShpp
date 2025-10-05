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
import com.example.androidcourseshpp.databinding.FragmentSignUpExtendedBinding
import com.example.androidcourseshpp.ui.screens.auth.AuthFragment

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
        formatMobilePhoneInput()
    }

    private fun setListeners() = with(binding) {
        buttonForward.setOnClickListener {
            moveToMyProfileScreen(args.userEmail)
        }
        buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun formatMobilePhoneInput() = with(binding) {
        editTextMobilePhone.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                s?.let {

                    if (BRACKET_POSITIONS.keys.contains(s.length) && count > 0 && before == 0) {
                        updateMobilePhoneInput(BRACKET_POSITIONS[s.length])
                    }
                    if (HYPHEN_POSITIONS.contains(s.length) && count > 0 && before == 0) {
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