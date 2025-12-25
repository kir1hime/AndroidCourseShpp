package com.example.androidcourseshpp.ui.screens.main.addcontacts

import android.widget.ImageView
import com.example.androidcourseshpp.data.userlist.UserItem
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class AddContactsContract {
    sealed interface Event : ViewEvent {
        data object OnArrowBackButtonClicked : Event
        data object OnSearchButtonClicked : Event
        data class OnUserItemClicked(val userItem: UserItem, val avatar: ImageView) : Event
        data object OnAddContactClicked : Event
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToDetailsScreen(
            val userItem: UserItem,
            val avatar: ImageView
        ) : Effect

        data object NavigateToContactListScreen : Effect
    }

    data class UIState(
        val userList: List<UserItem>,
        val isProgressBarShowed: Boolean
    ) : ViewState
}