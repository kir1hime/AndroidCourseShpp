package com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog

import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.domain.usecase.gallery.AddGalleryPhotoUseCase
import com.example.androidcourseshpp.domain.usecase.gallery.GetGalleryPhotosUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.model.GalleryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseProfilePhotoViewModel @Inject constructor(
    private val getGalleryPhotosUseCase: GetGalleryPhotosUseCase,
    private val addGalleryPhotoUseCase: AddGalleryPhotoUseCase
) :
    BaseViewModel<ChooseProfilePhotoContract.Event, ChooseProfilePhotoContract.Effect, ChooseProfilePhotoContract.UIState>() {

    init {
        viewModelScope.launch {
            getGalleryPhotosUseCase().collect { photos ->
                val photoList = photos.map { photo ->
                    GalleryItem(
                        id = photo.id,
                        photoURL = photo.photoURL
                    )
                }
                setState { copy(photoList = photoList) }
            }
        }
    }

    override fun initState() =
        ChooseProfilePhotoContract.UIState(emptyList())

    override fun handleEvent(event: ChooseProfilePhotoContract.Event) {
        when (event) {
            is ChooseProfilePhotoContract.Event.OnCancelButtonClicked -> navigateToParentFragment()
            is ChooseProfilePhotoContract.Event.OnOpenGalleryButtonClicked -> openPhoneGallery()
            is ChooseProfilePhotoContract.Event.PhotoChosen -> addNewPhotoToGallery(event.photo)
        }
    }

    private fun addNewPhotoToGallery(photo: String) {
        addGalleryPhotoUseCase(photo)
        setEffect(ChooseProfilePhotoContract.Effect.SendPhotoToParentFragment(photo))
        navigateToParentFragment()
    }

    private fun navigateToParentFragment() {
        setEffect(ChooseProfilePhotoContract.Effect.NavigateToParentFragment)
    }

    private fun openPhoneGallery() {
        setEffect(ChooseProfilePhotoContract.Effect.OpenPhoneGallery)
    }
}