package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.graphics.Bitmap
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.auth.signup.model.SignUpModel

class SignUpExtendedContract {
    sealed interface Event : ViewEvent {
        data object OnAddProfilePhotoImageViewClicked : Event
        data object OnCancelButtonClicked : Event
        data class OnForwardButtonClicked(
            val userName: String,
            val mobilePhone: String,
            val signUpUserInfo: SignUpModel,
            val avatar: Bitmap
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToPreviousScreen : Effect
        data object NavigateToChooseProfilePhotoDialog : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
        data class NavigateToUserProfileScreen(val userInfo: UserInfo) : Effect
    }

    data class UIState(
        val userNameHelperResId: Int,
        val mobilePhoneHelperResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState
}