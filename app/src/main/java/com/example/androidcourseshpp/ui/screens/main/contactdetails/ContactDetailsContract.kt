package com.example.androidcourseshpp.ui.screens.main.contactdetails

import androidx.annotation.StringRes
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel

class ContactDetailsContract {

    sealed class Event : ViewEvent {
        data class OnAddContactButtonClicked(val contactDetails: ContactDetailsModel) : Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowToast(@param:StringRes val toastMessageResId: Int) : Effect()
        data object ContactWasAdded : Effect()
    }

    data object UIState : ViewState
}