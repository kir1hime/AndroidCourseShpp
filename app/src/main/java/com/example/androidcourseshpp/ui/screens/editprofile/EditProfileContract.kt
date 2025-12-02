package com.example.androidcourseshpp.ui.screens.editprofile

import com.example.androidcourseshpp.data.network.service.user.entity.UpdateUserData
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import java.util.Date

class EditProfileContract {

    sealed interface Event : ViewEvent {
        data class OnSaveButtonClicked(val userServerId: Long, val updateUserData: UpdateUserData) :
            Event

        data object OnAddProfilePhotoImageViewClicked : Event
        data class SetUserInfo(val userServerId: Long) : Event
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToMyProfileScreen(val state: UIState) : Effect
        data object NavigateToChooseProfilePhotoDialog : Effect
    }

    data class UIState(
        val userName: String,
        val career: String,
        val mobilePhone: String,
        val address: String,
        val dateOfBirthday: Date?,
        val avatar: String,
        val isProgressBarShowed: Boolean
    ) : ViewState
}