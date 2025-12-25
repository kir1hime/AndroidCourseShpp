package com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class UserProfileContract {

    sealed interface Event : ViewEvent {
        data object OnViewMyContactsButtonClicked : Event
        data object OnLogOutButtonClicked : Event
        data object OnEditProfileClicked : Event

        data class UpdateUserInfo(val userServerId: Int) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToSignInScreen : Effect
        data object NavigateToContactList : Effect
        data class NavigateToEditProfileScreen(val userServerId: Int) : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
    }


    data class UIState(
        val userName: String,
        val career: String,
        val address: String,
        val avatar: String,
        val userServerId: Int
    ) : ViewState

}