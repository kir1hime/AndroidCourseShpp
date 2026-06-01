package com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog

import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.data.models.gallery.GalleryRepository
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseProfilePhotoViewModel @Inject constructor(private val galleryRepository: GalleryRepository) :
    BaseViewModel<ChooseProfilePhotoContract.Event, ChooseProfilePhotoContract.Effect, ChooseProfilePhotoContract.UIState>() {

    init {
        viewModelScope.launch {
            galleryRepository.galleryPhotos.collect { photos ->
                setState { copy(photoList = photos) }
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
        galleryRepository.addPhoto(photo)
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