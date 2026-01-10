package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.GalleryItem
import kotlinx.coroutines.flow.StateFlow

interface GalleryRepository {

    val galleryPhotos: StateFlow<List<GalleryItem>>

    fun addPhoto(photoURL: String)
}