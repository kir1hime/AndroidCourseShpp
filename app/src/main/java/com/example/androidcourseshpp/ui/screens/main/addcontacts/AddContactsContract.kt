package com.example.androidcourseshpp.ui.screens.main.addcontacts

import com.example.androidcourseshpp.ui.screens.main.addcontacts.entity.UserItem
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class AddContactsContract {
    sealed interface Event : ViewEvent {
        data object OnArrowBackButtonClicked : Event
        data object OnSearchButtonClicked : Event
        data object OnHideSearchButtonClicked : Event
        data object OnArrowTopFloatingButtonClicked : Event
        data object LoadUserList : Event
        data class OnUserItemClicked(val userItem: UserItem) : Event
        data class OnSearchBarTextChanged(val input: String) : Event
        data class SearchModeSwitched(val isSearchModeEnabled: Boolean) : Event
        data class OnAddContactClicked(
            val userItem: UserItem,
            val interruptProgressBar: () -> Unit
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object ShowSearchBar : Effect
        data object HideSearchBar : Effect
        data object ScrollUserListToTop : Effect
        data class NavigateToDetailsScreen(val userItem: UserItem) : Effect
        data class NavigateToContactListScreen(val isContactListChanged: Boolean) : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
    }

    data class UIState(
        val userList: List<UserItem>,
        val isProgressBarShowed: Boolean,
        val isTryAgainButtonShowed: Boolean,
        val isContactListChanged: Boolean,
        val isSearchMode: Boolean
    ) : ViewState
}