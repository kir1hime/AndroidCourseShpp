package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import android.graphics.Bitmap
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.editprofile.EditProfileContract
import com.github.javafaker.Avatar

class MyProfileContract {

    sealed interface Event : ViewEvent {
        data object OnViewMyContactsButtonClicked : Event
        data object OnLogOutButtonClicked : Event
        data object OnEditProfileClicked : Event

        data class UpdateUserInfo(val userServerId: Long) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToSignInScreen : Effect
        data object NavigateToContactList : Effect
        data object NavigateToEditProfileScreen : Effect
    }


    data class UIState(
        val userName: String,
        val career: String,
        val address: String,
        val avatar: String
    ) : ViewState

}