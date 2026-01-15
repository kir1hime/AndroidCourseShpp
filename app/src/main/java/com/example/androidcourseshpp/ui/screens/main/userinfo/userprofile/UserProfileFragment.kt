package com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentUserProfileBinding
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.USER_INFO
import com.example.androidcourseshpp.ui.utils.loadImageFromURLCircled
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity
import com.example.androidcourseshpp.ui.screens.main.editprofile.TO_UPDATE_USER_PROFILE
import com.example.androidcourseshpp.ui.screens.main.userinfo.TabSwitchable
import com.example.androidcourseshpp.ui.screens.main.userinfo.UserInfoFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {


    private val viewModel by viewModels<UserProfileViewModel>()

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserInfo()
        updateUserProfile()
        setListeners()
        setObservers()
        setOnBackPressedListener()
    }

    private fun setOnBackPressedListener() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun setUserInfo() {
        val userInfo = requireActivity().intent.getParcelableExtra<UserInfo>(USER_INFO)

        userInfo?.let {
            viewModel.setEvent(UserProfileContract.Event.SetUserInfo(userInfo))
        }
    }

    private fun updateUserProfile() {
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle

        val userInfoLiveData = savedStateHandle?.getLiveData<UserInfo>(TO_UPDATE_USER_PROFILE)

        userInfoLiveData?.observe(viewLifecycleOwner) { userInfo ->
            viewModel.setEvent(UserProfileContract.Event.SetUserInfo(userInfo))
        }
    }

    override fun setListeners() = with(binding) {
        buttonLogOut.setOnClickListener {
            viewModel.setEvent(UserProfileContract.Event.OnLogOutButtonClicked)
        }
        buttonViewMyContacts.setOnClickListener {
            viewModel.setEvent(UserProfileContract.Event.OnViewMyContactsButtonClicked)
        }
        buttonEditProfile.setOnClickListener {
            viewModel.setEvent(UserProfileContract.Event.OnEditProfileClicked)
        }
    }

    override fun setObservers() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is UserProfileContract.Effect.NavigateToContactList -> moveToMyContactsScreen()
                is UserProfileContract.Effect.NavigateToSignInScreen -> moveToSignUpScreen()
                is UserProfileContract.Effect.NavigateToEditProfileScreen -> moveToEditProfileScreen(
                    effect.userInfo
                )

                is UserProfileContract.Effect.ShowToast -> makeToast(effect.toastMessageResId)
            }
        }

        collectFlow(viewModel.state) { state ->
            textViewName.text = state.userInfo.name
            textViewCareer.updateIfNotEmpty(state.userInfo.career)
            textViewHomeAddress.updateIfNotEmpty(state.userInfo.address)
            circleViewProfilePhoto.loadImageFromURLCircled(
                requireContext(),
                state.userInfo.avatar,
                R.drawable.avatar
            )
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

    private fun moveToEditProfileScreen(userInfo: UserInfo) {
        val direction =
            UserInfoFragmentDirections.actionUserInfoFragmentToEditProfileFragment(userInfo)
        findNavController().navigate(direction)
    }
}