package com.example.androidcourseshpp.ui.screens.auth.splash

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class SplashContract {

    data object Sate : ViewState

    sealed interface Event : ViewEvent

    sealed interface Effect : ViewEffect {

        data class NavigateToMyProfileScreen(val userServerId: Long) : Effect
        data object NavigateToSignInScreen : Effect
    }

}