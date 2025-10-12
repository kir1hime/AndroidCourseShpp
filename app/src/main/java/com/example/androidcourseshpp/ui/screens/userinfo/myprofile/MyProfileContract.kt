package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class MyProfileContract {

    sealed interface Event : ViewEvent {
        data object OnViewMyContactsButtonClicked : Event
        data object OnLogOutButtonClicked : Event
        data class UserNameUpdated(val userName: String) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToSignUpScreen : Effect
        data object NavigateToContactList : Effect
    }

    data class UIState(val userName: String) : ViewState

}