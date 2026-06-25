package com.example.androidcourseshpp.data.source.local.userdata

interface GalleryDataProvider {
    fun saveUserGalleryPhotos(photoURLs: Set<String>)
    fun getUserGalleryPhotos(): Set<String>
    fun clearGalleryPhotos()
}