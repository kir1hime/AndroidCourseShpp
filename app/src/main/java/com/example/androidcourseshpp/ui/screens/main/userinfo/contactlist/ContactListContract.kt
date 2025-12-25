package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist

import android.widget.ImageView
import com.example.androidcourseshpp.data.contactlist.ContactItem
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class ContactListContract {

    sealed interface Event : ViewEvent {
        data object OnArrowBackButtonClicked : Event
        data object OnAddContactClicked : Event
        data object PhoneContactsAdded : Event
        data class ContactItemAdded(val contactItem: ContactItem, val position: Int) :
            Event

        data class OnItemClicked(
            val contact: ContactItem,
            val avatar: ImageView
        ) : Event

        data class ContactItemDeleted(
            val contactItem: ContactItem,
            val position: Int
        ) : Event

        data class OnDeleteSelectedItemsFloatingButtonClicked(
            val contactItems: List<ContactItem>
        ) : Event

        data class AddContactDialogEventProcessed(
            val contactName: String,
            val contactCareer: String
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToUserProfileScreen : Effect
        data class NavigateToDetailsScreen(
            val contact: ContactItem,
            val avatar: ImageView
        ) : Effect

        data object NavigateToAddContactsScreen : Effect
    }

    data class UIState(
        val contactList: List<ContactItem>,
        val isPhoneContactsLoaded: Boolean
    ) : ViewState
}