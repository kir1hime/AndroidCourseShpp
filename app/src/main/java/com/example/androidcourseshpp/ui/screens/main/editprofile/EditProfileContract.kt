package com.example.androidcourseshpp.ui.screens.main.editprofile

import com.example.androidcourseshpp.data.source.network.entity.user.UpdateUserData
import com.example.androidcourseshpp.domain.entity.UserInfo
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import java.util.Date

class EditProfileContract {

    sealed interface Event : ViewEvent {
        data object OnAddProfilePhotoImageViewClicked : Event
        data class SetUserInfo(val userInfo: UserInfo) : Event
        data class ProfilePhotoUpdated(val profilePhotoUrl: String) : Event
        data class UserNameUpdated(val userName: String) : Event
        data class CareerUpdated(val career: String) : Event
        data class MobilePhoneUpdated(val mobilePhone: String) : Event
        data class AddressUpdated(val address: String) : Event
        data class DateOfBirthdayUpdated(val dateOfBirthday: Date?) : Event
        data object OnSaveButtonClicked : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToChooseProfilePhotoDialog : Effect
        data class NavigateToUserProfileScreen(val isUserDataChanged: Boolean, val userInfo: UserInfo) : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
    }

    data class UIState(
        val userInfo: UserInfo,
        val isProgressBarShowed: Boolean,
        val isSaveButtonEnabled: Boolean,
        val isUserDataChanged: Boolean
    ) : ViewState
}