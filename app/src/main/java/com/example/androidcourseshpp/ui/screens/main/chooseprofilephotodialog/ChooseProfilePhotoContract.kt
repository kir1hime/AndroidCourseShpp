package com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog

import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.model.GalleryItem
import com.example.androidcourseshpp.ui.ViewEffect
import com.example.androidcourseshpp.ui.ViewEvent
import com.example.androidcourseshpp.ui.ViewState

class ChooseProfilePhotoContract {

    sealed interface Event : ViewEvent {
        data object OnCancelButtonClicked : Event
        data object OnOpenGalleryButtonClicked : Event
        data class PhotoChosen(val photo: String) : Event
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToParentFragment : Effect
        data object OpenPhoneGallery : Effect
        data class SendPhotoToParentFragment(val photo: String) : Effect
    }

    data class UIState(
        val photoList: List<GalleryItem>
    ) : ViewState
}