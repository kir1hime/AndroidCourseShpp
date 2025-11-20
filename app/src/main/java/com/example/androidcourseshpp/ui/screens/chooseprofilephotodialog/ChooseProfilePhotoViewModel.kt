package com.example.androidcourseshpp.ui.screens.chooseprofilephotodialog

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.gallery.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseProfilePhotoViewModel @Inject constructor(galleryRepository: GalleryRepository) : ViewModel() {

    val galleryPhotos = galleryRepository.galleryPhotos
}