package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist

import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.entity.ContactItem
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class ContactListContract {

    sealed interface Event : ViewEvent {
        data object OnArrowBackButtonClicked : Event
        data object OnAddContactClicked : Event
        data object OnSearchButtonClicked : Event
        data object OnHideSearchButtonCLicked : Event
        data object LoadContactList : Event
        data class OnSearchBarTextChanged(val input: String) : Event

        data class SearchModeSwitched(val isSearchModeEnabled: Boolean) : Event
        data class ContactItemAdded(val contactItem: ContactItem) :
            Event

        data class OnItemClicked(
            val contact: ContactItem
        ) : Event

        data class ContactItemDeleted(
            val contactItem: ContactItem
        ) : Event

        data class OnDeleteSelectedItemsFloatingButtonClicked(
            val contactItems: List<ContactItem>
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object ShowSearchBar : Effect
        data object HideSearchBar : Effect
        data object NavigateToUserProfileScreen : Effect
        data object NavigateToAddContactsScreen : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
        data class NavigateToDetailsScreen(
            val contact: ContactItem
        ) : Effect

        data class ShowUndoDeletingItemSnackBar(val deletedItem: ContactItem) : Effect
    }

    data class UIState(
        val contactList: List<ContactItem>,
        val isProgressBarShowed: Boolean,
        val isTryAgainButtonShowed: Boolean,
        val isSearchMode: Boolean
    ) : ViewState
}