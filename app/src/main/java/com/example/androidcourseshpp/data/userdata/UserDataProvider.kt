package com.example.androidcourseshpp.data.userdata

interface UserDataProvider {
    fun saveUserServerId(userServerId: Long)
    fun getUserServerId(): Long
    fun clearUserServerId()
    fun saveUserAvatarUrl(avatar: String)
    fun getUserAvatarUrl(): String
    fun clearUserAvatarUrl()
    fun saveUserGalleryPhotos(photoURLs: Set<String>)
    fun getUserGalleryPhotos(): Set<String>
}