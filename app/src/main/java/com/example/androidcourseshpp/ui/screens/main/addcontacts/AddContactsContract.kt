package com.example.androidcourseshpp.ui.screens.main.addcontacts

import com.example.androidcourseshpp.data.userlist.UserItem
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class AddContactsContract {
    sealed interface Event: ViewEvent{

    }

    sealed interface Effect: ViewEffect{

    }

    data class UIState(
        val userList: List<UserItem>,
        val isProgressBarShowed: Boolean
    ): ViewState
}