package com.example.androidcourseshpp.data.local.userdata

interface GalleryDataProvider {
    fun saveUserGalleryPhotos(photoURLs: Set<String>)
    fun getUserGalleryPhotos(): Set<String>
    fun clearGalleryPhotos()
}