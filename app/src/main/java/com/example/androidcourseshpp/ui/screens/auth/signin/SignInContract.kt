package com.example.androidcourseshpp.ui.screens.auth.signin

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class SignInContract {

    sealed interface Event : ViewEvent {
        object OnSignUpLabelClicked : Event
        data class OnLoginButtonClicked(
            val email: String,
            val password: String,
            val toRememberUser: Boolean
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToSingUpScreen : Effect

        data class NavigateToUserProfileScreen(val userServerId: Int) : Effect

        data class ShowToast(val toastMessageResId: Int) : Effect
    }

    data class UIState(
        val eMailHelperTextResId: Int,
        val passwordHelperTextResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState
}