package com.example.androidcourseshpp.ui.screens.auth.signup

import com.example.androidcourseshpp.ui.UserInfoEntity
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.SignUpUserInfo

class SignUpContract {

    sealed interface Event : ViewEvent {
        data class OnResisterButtonClicked(
            val email: String,
            val password: String,
            val toRememberUser: Boolean
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToSignUpExtended(
            val signUpUserInfo: SignUpUserInfo
        ) : Effect

        data class ShowToast(val toastMessageResId: Int) : Effect
    }

    data class UIState(
        val eMailHelperTextResId: Int,
        val passwordHelperTextResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState

}