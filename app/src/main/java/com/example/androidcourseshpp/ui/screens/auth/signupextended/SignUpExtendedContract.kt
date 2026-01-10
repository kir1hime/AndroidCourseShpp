package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.graphics.Bitmap
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.auth.signupextended.entity.SignUpUserInfoEntity

class SignUpExtendedContract {
    sealed interface Event : ViewEvent {
        data object OnAddProfilePhotoImageViewClicked : Event
        data object OnCancelButtonClicked : Event
        data class OnForwardButtonClicked(
            val userName: String,
            val mobilePhone: String,
            val signUpUserInfo: SignUpUserInfoEntity,
            val avatar: Bitmap
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToPreviousScreen : Effect
        data object NavigateToChooseProfilePhotoDialog : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
        data class NavigateToUserProfileScreen(val userServerId: Int) : Effect
    }

    data class UIState(
        val userNameHelperResId: Int,
        val mobilePhoneHelperResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState
}