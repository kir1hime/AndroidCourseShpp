package com.example.androidcourseshpp.domain.repository

interface UserLocalRepository {
    fun saveUserServerId(userServerId: Int)
    fun getUserServerId(): Int
    fun clearUserServerId()
    fun saveUserAvatarUrl(avatar: String)
    fun getUserAvatarUrl(): String
    fun clearUserAvatarUrl()

    fun saveUserGalleryPhotos(photoURLs: Set<String>)
    fun getUserGalleryPhotos(): Set<String>
}