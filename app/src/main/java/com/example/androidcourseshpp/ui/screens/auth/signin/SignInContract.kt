package com.example.androidcourseshpp.ui.screens.auth.signin

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class SignInContract {

    sealed interface Event : ViewEvent {
        object OnLoginButtonClicked : Event
        object OnSignUpLabelClicked: Event
    }

    sealed interface Effect : ViewEffect {
        object NavigateToSingUpScreen : Effect
        object NavigateToMyProfileScreen : Effect
    }

    data class UIState(
        val eMailHelperTextResId: Int,
        val passwordHelperTextResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState
}