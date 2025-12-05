package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.dataProvider.USER_SERVER_ID
import com.example.androidcourseshpp.databinding.FragmentMyProfileBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.extensions.loadImageFromURLCircled
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity
import com.example.androidcourseshpp.ui.screens.userinfo.TabSwitchable
import com.example.androidcourseshpp.ui.screens.userinfo.UserInfoFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import kotlin.properties.Delegates

@AndroidEntryPoint
class MyProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentMyProfileBinding

    private val viewModel by viewModels<MyProfileViewModel>()

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

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
        setUserInfo()
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
        val userServerId = requireActivity().intent.getLongExtra(USER_SERVER_ID, DEFAULT_ID_VALUE)
        viewModel.setEvent(MyProfileContract.Event.UpdateUserInfo(userServerId))
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
                    effect.userServerId
                )
            }
        }

        collectFlow(viewModel.state) { state ->
            textViewName.text = state.userName
            textViewCareer.updateIfNotEmpty(state.career)
            textViewHomeAddress.updateIfNotEmpty(state.address)
            circleViewProfilePhoto.loadImageFromURLCircled(
                requireContext(),
                state.avatar,
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

    private fun moveToEditProfileScreen(userServerId: Long) {
        val direction =
            UserInfoFragmentDirections.actionUserInfoFragmentToEditProfileFragment(userServerId)
        findNavController().navigate(direction)
    }
}