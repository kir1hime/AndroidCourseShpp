package com.example.androidcourseshpp.data.models.gallery

import kotlinx.coroutines.flow.StateFlow

interface GalleryRepository {

    val galleryPhotos: StateFlow<List<GalleryItem>>

    fun addPhoto(photoURL: String)
}