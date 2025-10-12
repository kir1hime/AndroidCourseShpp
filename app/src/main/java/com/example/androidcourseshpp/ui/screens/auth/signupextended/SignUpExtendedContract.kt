package com.example.androidcourseshpp.ui.screens.auth.signupextended

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class SignUpExtendedContract {
    sealed interface Event : ViewEvent {
        data class OnForwardButtonClicked(val userName: String, val mobilePhone: String) : Event
        data object OnAddProfilePhotoImageViewClicked : Event
        data object OnCancelButtonClicked : Event
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToMyProfileScreen(val email : String) : Effect
        data object NavigateToPreviousScreen : Effect
        data object NavigateToChooseProfilePhotoDialog: Effect
    }

    data class UIState(
        val userNameHelperResId: Int,
        val mobilePhoneHelperResId: Int
    ) : ViewState
}