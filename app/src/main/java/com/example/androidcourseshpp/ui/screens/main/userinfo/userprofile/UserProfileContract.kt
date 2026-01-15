package com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class UserProfileContract {

    sealed interface Event : ViewEvent {
        data object OnViewMyContactsButtonClicked : Event
        data object OnLogOutButtonClicked : Event
        data object OnEditProfileClicked : Event
        data class SetUserInfo(val userInfo: UserInfo) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToSignInScreen : Effect
        data object NavigateToContactList : Effect
        data class NavigateToEditProfileScreen(val userInfo: UserInfo) : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
    }


    data class UIState(
        val userInfo: UserInfo
    ) : ViewState

}