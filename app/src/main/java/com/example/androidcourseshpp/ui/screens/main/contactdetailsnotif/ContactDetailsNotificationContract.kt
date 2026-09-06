package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import androidx.annotation.StringRes
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel

class ContactDetailsNotificationContract {
    sealed interface Event : ViewEvent {

        data class OnAddContactButtonClicked(val contactDetails: ContactDetailsModel) : Event
        data class OnDeleteContactButtonClicked(val contactDetails: ContactDetailsModel) : Event
        data object OnArrowBackButtonClicked : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToPreviousScreen : Effect
        data class ShowToast(@param:StringRes val message: Int) : Effect
        data object MainActionWasExecuted : Effect
    }

    data object UIState : ViewState
}