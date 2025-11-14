package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.editprofile.EditProfileContract

class MyProfileContract {

    sealed interface Event : ViewEvent {
        data object OnViewMyContactsButtonClicked : Event
        data object OnLogOutButtonClicked : Event
        data class SetUserName(val name: String) : Event
        data object OnEditProfileClicked : Event
        data class SetUserInfo(val state: UIState) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToSignInScreen : Effect
        data object NavigateToContactList : Effect
        data class NavigateToEditProfileScreen(val state: UIState) : Effect
    }


    data class UIState(
        val userName: String,
        val career: String,
        val address: String,
        val mobilePhone: String,
        val dateOfBirthday: String
    ) : ViewState

}