package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.gallery.GalleryItemInfo
import kotlinx.coroutines.flow.StateFlow

interface GalleryRepository {
    val galleryPhotos: StateFlow<List<GalleryItemInfo>>

    fun addPhoto(photoURL: String)
    fun clearGalleryPhotos()
}