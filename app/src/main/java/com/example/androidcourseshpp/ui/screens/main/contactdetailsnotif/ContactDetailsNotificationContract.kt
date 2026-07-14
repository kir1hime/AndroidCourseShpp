package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import androidx.annotation.StringRes
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.notifications.NotificationAction

class ContactDetailsNotificationContract {
    sealed interface Event : ViewEvent {
        data class OnMainActionButtonClicked(
            val action: NotificationAction,
            val contactId: Long
        ) : Event

        data object OnArrowBackButtonClicked : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToPreviousScreen : Effect
        data class ShowToast(@StringRes val message: Int) : Effect
    }

    data object UIState : ViewState
}