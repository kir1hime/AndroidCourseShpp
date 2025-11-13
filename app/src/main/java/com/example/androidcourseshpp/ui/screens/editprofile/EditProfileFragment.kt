package com.example.androidcourseshpp.ui.screens.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.databinding.FragmentEditProfileBinding
import com.example.androidcourseshpp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel by viewModels<EditProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        formatMobilePhoneInput(binding.editTextMobilePhone)
    }

    private fun setListeners() = with(binding) {
        buttonSave.setOnClickListener {

        }
        imageButtonAddProfilePhoto.setOnClickListener {

        }
    }

    private fun setObservers() = with(binding) {
        collectFlow(viewModel.state) { state ->
            editTextUsername.setText(state.userName)
            editTextCareer.setText(state.career)
            editTextAddress.setText(state.address)
            editTextMobilePhone.setText(state.mobilePhone)
            editTextDateOfBirthday.setText(state.dateOfBirthday)
        }

        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is EditProfileContract.Effect.NavigateToChooseProfilePhotoDialog -> moveToChooseProfilePhotoDialog()
                is EditProfileContract.Effect.NavigateToMyProfileScreen -> moveToMyProfileScreen(
                    EditProfileEntity(
                        userName = effect.state.userName,
                        mobilePhone = effect.state.mobilePhone,
                        address = effect.state.address,
                        career = effect.state.career,
                        dateOfBirthday = effect.state.dateOfBirthday
                    )
                )
            }
        }
    }

    private fun moveToMyProfileScreen(userData: EditProfileEntity) {
    }

    private fun moveToChooseProfilePhotoDialog() {
        val direction =
            EditProfileFragmentDirections.actionEditProfileFragmentToChooseProfilePhotoDialog2()
        findNavController().navigate(direction)
    }

}