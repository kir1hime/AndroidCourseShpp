package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentMyProfileBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.UserInfoEntity
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity
import com.example.androidcourseshpp.ui.screens.auth.USER_NAME
import com.example.androidcourseshpp.ui.screens.userinfo.TabSwitchable
import com.example.androidcourseshpp.ui.screens.userinfo.UserInfoFragmentDirections
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
        setResultListenerFromEditProfile()
        defineUserName()
        setListeners()
        setObservers()
    }

    private fun setResultListenerFromEditProfile() {
        val userInfo = setResultListener<UserInfoEntity>()
        userInfo?.value?.let { userInfo ->
            userInfo.apply {
                viewModel.setEvent(
                    MyProfileContract.Event.SetUserInfo(
                        MyProfileContract.UIState(
                            userName = userName,
                            career = career,
                            mobilePhone = mobilePhone,
                            address = address,
                            dateOfBirthday = dateOfBirthday
                        )
                    )
                )
            }
        }
    }

    private fun defineUserName() = with(binding) {
        val userName = requireActivity().intent.getStringExtra(USER_NAME) ?: ""
        viewModel.setEvent(MyProfileContract.Event.SetUserName(userName))
    }

    private fun setListeners() = with(binding) {
        buttonLogOut.setOnClickListener {
            viewModel.setEvent(MyProfileContract.Event.OnLogOutButtonClicked)
        }
        buttonViewMyContacts.setOnClickListener {
            viewModel.setEvent(MyProfileContract.Event.OnViewMyContactsButtonClicked)
        }
        buttonEditProfile.setOnClickListener {
            viewModel.setEvent(MyProfileContract.Event.OnEditProfileClicked)
        }
    }

    private fun setObservers() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is MyProfileContract.Effect.NavigateToContactList -> moveToMyContactsScreen()
                is MyProfileContract.Effect.NavigateToSignInScreen -> moveToSignUpScreen()
                is MyProfileContract.Effect.NavigateToEditProfileScreen -> moveToEditProfileScreen(
                    UserInfoEntity(
                        userName = effect.state.userName,
                        mobilePhone = effect.state.mobilePhone,
                        address = effect.state.address,
                        career = effect.state.career,
                        dateOfBirthday = effect.state.dateOfBirthday
                    )
                )
            }
        }
        collectFlow(viewModel.state) { state ->
            textViewName.text = state.userName
            Log.d("myTag", state.career)
            textViewCareer.updateIfNotEmpty(state.career)
            textViewHomeAddress.updateIfNotEmpty(state.address)
        }
    }

    fun TextView.updateIfNotEmpty(newValue: String) {
        if (newValue.isNotEmpty()) {
            text = newValue
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

    private fun moveToEditProfileScreen(userInfo: UserInfoEntity) {
        val direction =
            UserInfoFragmentDirections.actionUserInfoFragmentToEditProfileFragment(userInfo)
        findNavController().navigate(direction)
    }
}