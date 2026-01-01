package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist

import com.example.androidcourseshpp.data.models.contactlist.ContactItem
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class ContactListContract {

    sealed interface Event : ViewEvent {
        data object OnArrowBackButtonClicked : Event
        data object OnAddContactClicked : Event
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

        data object LoadContactList : Event
        data class OnSearchBarTextChanged(val input: String) : Event
        data object OnSearchButtonClicked : Event
        data object OnHideSearchButtonCLicked : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToUserProfileScreen : Effect
        data class NavigateToDetailsScreen(
            val contact: ContactItem
        ) : Effect

        data object NavigateToAddContactsScreen : Effect
        data class ShowToast(val toastMessageResId: Int) : Effect
        data object ShowSearchBar : Effect
        data object HideSearchBar : Effect
    }

    data class UIState(
        val contactList: List<ContactItem>,
        val isProgressBarShowed: Boolean,
        val isTryAgainButtonShowed: Boolean
    ) : ViewState
}