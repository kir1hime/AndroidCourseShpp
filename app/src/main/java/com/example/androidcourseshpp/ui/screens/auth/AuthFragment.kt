package com.example.androidcourseshpp.ui.screens.auth

import android.app.ActivityOptions
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.viewbinding.ViewBinding
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentSignInBinding
import com.example.androidcourseshpp.databinding.FragmentSignUpBinding
import com.example.androidcourseshpp.databinding.FragmentSignUpExtendedBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.MainActivity

const val USER_EMAIL = "userEmail"

open class AuthFragment : BaseFragment() {
    fun moveToMyProfileScreen(userEmail: String) {
        val intent = Intent(requireContext(), MainActivity::class.java)

        intent.putExtras(bundleOf(USER_EMAIL to userEmail))

        val options = ActivityOptions.makeCustomAnimation(
            requireContext(),
            R.anim.slide_in_from_right_to_left,
            R.anim.slide_out_from_right_to_left
        )

        startActivity(intent, options.toBundle())
        requireActivity().finish()
    }

    fun <T : ViewBinding> setLoadingState(isLoaded: Boolean, binding: T) {
        val isEnabled = !isLoaded

        when (binding) {
            is FragmentSignInBinding -> with(binding) {
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

            is FragmentSignUpBinding -> with(binding) {
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

            is FragmentSignUpExtendedBinding -> with(binding) {
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
        }
    }

}

