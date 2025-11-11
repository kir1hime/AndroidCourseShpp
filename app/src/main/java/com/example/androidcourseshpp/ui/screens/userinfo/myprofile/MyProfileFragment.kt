package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentMyProfileBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity
import com.example.androidcourseshpp.ui.screens.auth.USER_NAME
import com.example.androidcourseshpp.ui.screens.userinfo.TabSwitchable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        defineUserName()
        setListeners()
        setObservers()
    }

    private fun defineUserName() = with(binding) {
        val userName = requireActivity().intent.getStringExtra(USER_NAME) ?: ""
        Log.d("myTag", userName)
        viewModel.setEvent(MyProfileContract.Event.SetUserName(userName))
    }

    private fun setListeners() = with(binding) {
        buttonLogOut.setOnClickListener {
            viewModel.setEvent(MyProfileContract.Event.OnLogOutButtonClicked)
        }
        buttonViewMyContacts.setOnClickListener {
            viewModel.setEvent(MyProfileContract.Event.OnViewMyContactsButtonClicked)
        }
    }

    private fun setObservers() {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is MyProfileContract.Effect.NavigateToContactList -> moveToMyContactsScreen()
                is MyProfileContract.Effect.NavigateToSignInScreen -> moveToSignUpScreen()
            }
        }
        collectFlow(viewModel.state) { state ->
            binding.textViewName.text = state.userName
        }
    }

    private fun moveToSignUpScreen() {
        val intent = Intent(requireContext(), AuthActivity::class.java)

        val options = ActivityOptions.makeCustomAnimation(
            requireContext(),
            R.anim.slide_in_from_left_to_right,
            R.anim.slide_out_from_left_to_right
        )

        startActivity(intent, options.toBundle())
        requireActivity().finish()

    }

    private fun moveToMyContactsScreen() {
        val parentFragment = parentFragment as? TabSwitchable
        parentFragment?.moveToContactsTab()
    }
}