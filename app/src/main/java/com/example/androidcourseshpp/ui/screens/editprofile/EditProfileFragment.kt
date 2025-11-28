package com.example.androidcourseshpp.ui.screens.editprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.databinding.FragmentEditProfileBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.UserInfoEntity
import com.example.androidcourseshpp.ui.screens.userinfo.myprofile.MyProfileEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel by viewModels<EditProfileViewModel>()
    private val args: EditProfileFragmentArgs by navArgs()

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
        setUserInfo()
        setListeners()
        setObservers()
        formatMobilePhoneInput(binding.editTextMobilePhone)
    }

    private fun setUserInfo() = with(binding) {
        args.userInfo.apply {
            viewModel.setEvent(
                EditProfileContract.Event.SetUserInfo(
                    EditProfileContract.UIState(
                        userName = userName,
                        career = career,
                        mobilePhone = mobilePhone,
                        address = address,
                        dateOfBirthday = dateOfBirthday,
                        avatar = avatar
                    )
                )
            )
        }
    }


    private fun setListeners() = with(binding) {
        buttonSave.setOnClickListener {
            viewModel.setEvent(
                EditProfileContract.Event.OnSaveButtonClicked(
                    EditProfileContract.UIState(
                        userName = editTextUsername.text.toString(),
                        career = editTextCareer.text.toString(),
                        address = editTextAddress.text.toString(),
                        mobilePhone = editTextMobilePhone.text.toString(),
                        dateOfBirthday = editTextDateOfBirthday.text.toString(),
                        avatar = ""

                    )
                )
            )
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
                is EditProfileContract.Effect.NavigateToMyProfileScreen -> {
                    moveBackToMyProfileScreen(
                        UserInfoEntity(
                            userName = effect.state.userName,
                            mobilePhone = effect.state.mobilePhone,
                            address = effect.state.address,
                            career = effect.state.career,
                            dateOfBirthday = effect.state.dateOfBirthday,
                            avatar = effect.state.avatar
                        )
                    )
                }
            }
        }
    }

    fun moveBackToMyProfileScreen(userData: UserInfoEntity) {
        setResultForPreviousScreen(userData)
        findNavController().navigateUp()
    }

    private fun moveToChooseProfilePhotoDialog() {
        val direction =
            EditProfileFragmentDirections.actionEditProfileFragmentToChooseProfilePhotoDialog2()
        findNavController().navigate(direction)
    }

}