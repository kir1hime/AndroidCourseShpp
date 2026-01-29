package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import androidx.annotation.StringRes
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class ContactDetailsNotificationContract {
    sealed interface Event : ViewEvent {
        data class MainActionButtonClicked(
            val action: NotificationAction,
            val contactId: Int
        ) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateUp : Effect
        data class ShowToast(@StringRes val message: Int) : Effect
    }

    object UIState : ViewState
}