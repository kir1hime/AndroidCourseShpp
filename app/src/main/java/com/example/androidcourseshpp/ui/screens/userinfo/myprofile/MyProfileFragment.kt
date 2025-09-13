package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.androidcourseshpp.databinding.FragmentMyProfileBinding
import com.example.androidcourseshpp.ui.BaseFragment


class MyProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentMyProfileBinding
    private val viewModel by viewModels<MyProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

   /* private fun defineUserName() = with(viewModel.savedEMail) {
        binding.tvName.text = if (value == "") EmailParser.parseEMail(
            intent.getStringExtra(EMAIL_KEY).toString()
        ) else
            EmailParser.parseEMail(value)
    }


    private fun setListeners() = with(binding) {
        btLogOut.setOnClickListener {
            moveToSignUpScreen()
            viewModel.deleteUserInfo()

        }
        btViewMyContacts.setOnClickListener {
            moveToMyContactsScreen()
        }
    }

    private fun moveToSignUpScreen() {
        val intent = Intent(this, AuthActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_from_left_to_right,
            R.anim.slide_out_from_left_to_right
        )

        startActivity(intent, options.toBundle())
        finish()

    }

    private fun moveToMyContactsScreen() {
        val intent = Intent(this, ContactsActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_from_right_to_left,
            R.anim.slide_out_from_right_to_left
        )
        startActivity(intent, options.toBundle())
        finish()
    }*/
}