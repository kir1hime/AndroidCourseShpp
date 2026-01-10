package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.entity.GalleryItem
import kotlinx.coroutines.flow.StateFlow

interface GalleryRepository {

    val galleryPhotos: StateFlow<List<GalleryItem>>

    fun addPhoto(photoURL: String)
}