package com.example.androidcourseshpp.ui.screens.auth.signup

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class SignUpContract {

    sealed interface Event : ViewEvent {
        data class OnResisterButtonClicked(
            val email: String,
            val password: String,
            val rememberUserData: Boolean
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToSignUpExtended(val email: String, val password: String) : Effect
    }

    data class UIState(
        val eMailHelperTextResId: Int,
        val passwordHelperTextResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState

}