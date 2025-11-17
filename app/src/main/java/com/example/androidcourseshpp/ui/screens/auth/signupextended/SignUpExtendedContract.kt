package com.example.androidcourseshpp.ui.screens.auth.signupextended

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.SignUpUserInfo
import com.example.androidcourseshpp.ui.screens.UserInfoEntity

class SignUpExtendedContract {
    sealed interface Event : ViewEvent {
        data class OnForwardButtonClicked(
            val userName: String,
            val mobilePhone: String,
            val signUpUserInfo: SignUpUserInfo
        ) : Event

        data object OnAddProfilePhotoImageViewClicked : Event
        data object OnCancelButtonClicked : Event
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToMyProfileScreen(val userInfo: UserInfoEntity) : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
        data object NavigateToPreviousScreen : Effect
        data object NavigateToChooseProfilePhotoDialog : Effect
    }

    data class UIState(
        val userNameHelperResId: Int,
        val mobilePhoneHelperResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState
}