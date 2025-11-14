package com.example.androidcourseshpp.ui.screens.editprofile

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class EditProfileContract {

    sealed interface Event : ViewEvent {
        data object OnSaveButtonClicked : Event
        data object OnAddProfilePhotoImageViewClicked : Event
        data class SetUserInfo(val state: UIState) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToMyProfileScreen : Effect
        data object NavigateToChooseProfilePhotoDialog : Effect
    }

    data class UIState(
        val userName: String,
        val career: String,
        val mobilePhone: String,
        val address: String,
        val dateOfBirthday: String
    ) : ViewState
}