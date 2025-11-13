package com.example.androidcourseshpp.ui.screens.auth.signupextended

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class SignUpExtendedContract {
    sealed interface Event : ViewEvent {
        data class OnForwardButtonClicked(
            val userName: String,
            val mobilePhone: String,
            val serverUserId: Long
        ) : Event

        data object OnAddProfilePhotoImageViewClicked : Event
        data object OnCancelButtonClicked : Event
        data class SaveUserName(val name: String) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToMyProfileScreen : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
        data object NavigateToPreviousScreen : Effect
        data object NavigateToChooseProfilePhotoDialog : Effect
    }

    data class UIState(
        val userNameHelperResId: Int,
        val mobilePhoneHelperResId: Int,
        val isProgressBarShowed: Boolean
    ) : ViewState
}