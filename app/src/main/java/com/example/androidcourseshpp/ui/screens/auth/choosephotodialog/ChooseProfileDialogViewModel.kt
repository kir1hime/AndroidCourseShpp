package com.example.androidcourseshpp.ui.screens.auth.choosephotodialog

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.gallery.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseProfileDialogViewModel @Inject constructor(galleryRepository: GalleryRepository) : ViewModel() {

    val galleryPhotos = galleryRepository.galleryPhotos
}