package com.example.androidcourseshpp.ui.screens.auth.signin

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class SignInContract {

    sealed interface Event : ViewEvent {
        data class OnLoginButtonClicked(
            val email: String,
            val password: String,
            val toRememberUser: Boolean
        ) : Event

        object OnSignUpLabelClicked : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToSingUpScreen : Effect
        data class NavigateToMyProfileScreen(val userName: String) : Effect

        data class ShowToast(val toastMessageResId: Int) : Effect
    }

    data class UIState(
        val eMailHelperTextResId: Int,
        val passwordHelperTextResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState
}