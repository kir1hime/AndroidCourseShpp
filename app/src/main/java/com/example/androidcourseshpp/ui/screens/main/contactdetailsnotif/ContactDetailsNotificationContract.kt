package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel

class ContactDetailsNotificationContract {
    sealed interface Event : ViewEvent {
        data class MainActionButtonClicked(
            val action: NotificationAction,
            val contactDetails: ContactDetailsModel
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateUp : Effect
    }

    object UIState : ViewState
}