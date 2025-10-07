package com.example.androidcourseshpp.ui.screens.auth

import android.app.ActivityOptions
import android.content.Intent
import androidx.core.os.bundleOf
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.MainActivity

const val USER_EMAIL = "userEmail"

open class AuthFragment : BaseFragment() {
     fun moveToMyProfileScreen(userEmail : String) {
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
}

