package com.example.androidcourseshpp.data.local.userdata

interface GalleryDataProvider {
    suspend fun saveUserGalleryPhotos(photosURLs: Set<String>)
    suspend fun getUserGalleryPhotos(): Set<String>?
    suspend fun clearGalleryPhotos()
}