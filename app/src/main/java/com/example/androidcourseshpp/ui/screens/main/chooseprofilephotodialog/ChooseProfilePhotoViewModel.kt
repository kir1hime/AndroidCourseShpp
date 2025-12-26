package com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.models.gallery.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseProfilePhotoViewModel @Inject constructor(galleryRepository: GalleryRepository) :
    ViewModel() {

    val galleryPhotos = galleryRepository.galleryPhotos
}