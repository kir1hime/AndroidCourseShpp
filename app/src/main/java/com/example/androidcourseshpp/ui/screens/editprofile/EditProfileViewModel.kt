package com.example.androidcourseshpp.ui.screens.editprofile

import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor() :
    BaseViewModel<EditProfileContract.Event, EditProfileContract.Effect, EditProfileContract.UIState>() {

    override fun initState(): EditProfileContract.UIState {
        return (EditProfileContract.UIState(
            userName = "",
            mobilePhone = "",
            career = "",
            address = "",
            dateOfBirthday = ""
        ))
    }

    override fun handleEvent(event: EditProfileContract.Event) {
        when (event) {
            is EditProfileContract.Event.OnAddProfilePhotoImageViewClicked -> navigateToChooseProfilePhotoDialog()
            is EditProfileContract.Event.OnSaveButtonClicked -> navigateToMyProfileScreen()
            is EditProfileContract.Event.SetUserInfo -> updateState(event.state)
        }
    }

    private fun updateState(state: EditProfileContract.UIState) {
        setState {
            with(state) {
                copy(
                    userName = userName,
                    career = career,
                    mobilePhone = mobilePhone,
                    address = address,
                    dateOfBirthday = dateOfBirthday
                )
            }
        }
    }

    private fun navigateToMyProfileScreen() {
        setEffect(EditProfileContract.Effect.NavigateToMyProfileScreen)
    }

    private fun navigateToChooseProfilePhotoDialog() {
        setEffect(EditProfileContract.Effect.NavigateToChooseProfilePhotoDialog)
    }
}