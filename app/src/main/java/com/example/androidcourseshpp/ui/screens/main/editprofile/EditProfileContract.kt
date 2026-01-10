package com.example.androidcourseshpp.ui.screens.main.editprofile

import com.example.androidcourseshpp.data.source.network.entity.user.UpdateUserData
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import java.util.Date

class EditProfileContract {

    sealed interface Event : ViewEvent {
        data object OnAddProfilePhotoImageViewClicked : Event
        data class SetUserInfo(val userServerId: Int) : Event
        data class ProfilePhotoUpdated(val profilePhotoUrl: String) : Event
        data class UserNameUpdated(val userName: String) : Event
        data class CareerUpdated(val career: String) : Event
        data class MobilePhoneUpdated(val mobilePhone: String) : Event
        data class AddressUpdated(val address: String) : Event
        data class DateOfBirthdayUpdated(val dateOfBirthday: Date?) : Event
        data class OnSaveButtonClicked(
            val userServerId: Int,
            val updateUserData: UpdateUserData
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToChooseProfilePhotoDialog : Effect
        data object NavigateToUserProfileScreen : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
    }

    data class UIState(
        val userName: String,
        val career: String,
        val mobilePhone: String,
        val address: String,
        val dateOfBirthday: Date?,
        val avatar: String,
        val isProgressBarShowed: Boolean,
        val isSaveButtonEnabled: Boolean
    ) : ViewState
}