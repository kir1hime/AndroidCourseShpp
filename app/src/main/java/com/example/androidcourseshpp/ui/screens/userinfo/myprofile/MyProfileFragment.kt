package com.example.androidcourseshpp.ui.screens.userinfo.myprofile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidcourseshpp.data.EmailParser
import com.example.androidcourseshpp.data.dataProvider.EMAIL_KEY
import com.example.androidcourseshpp.databinding.FragmentMyprofileBinding
import com.example.androidcourseshpp.ui.utils.navigator
import com.example.androidcourseshpp.data.Tab
import com.example.androidcourseshpp.ui.screens.TabSwitchable

import com.example.androidcourseshpp.ui.utils.factory

class MyProfileFragment : Fragment() {

    private lateinit var binding: FragmentMyprofileBinding

    private val viewModel by viewModels<MyProfileViewModel> { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyprofileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defineUserName()
        setListeners()
    }

    private fun defineUserName() {
        viewModel.savedEMail.value?.let {
            binding.tvName.text = EmailParser.parseEMail(it)
        }
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
        navigator().moveToAuthScreen()
    }

    private fun moveToMyContactsScreen() {
        val parentFragment = parentFragment as? TabSwitchable
        parentFragment?.moveToContactsTab()
    }
}