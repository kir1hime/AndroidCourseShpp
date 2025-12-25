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
        data class OnUserItemClicked(val userItem: UserItem) : Event
        data class OnAddContactClicked(
            val userItem: UserItem,
            val interruptProgressBar: () -> Unit
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToDetailsScreen(
            val userItem: UserItem
        ) : Effect

        data object NavigateToContactListScreen : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
    }

    data class UIState(
        val userList: List<UserItem>,
        val isProgressBarShowed: Boolean,
    ) : ViewState
}