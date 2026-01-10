package com.example.androidcourseshpp.data.source.local.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val userDataProvider: UserDataProvider) : UserRepository {
    override fun saveUserServerId(userServerId: Int) {
        userDataProvider.saveUserServerId(userServerId)
    }

    override fun getUserServerId(): Int {
        return userDataProvider.getUserServerId()
    }

    override fun clearUserServerId() {
        userDataProvider.clearUserServerId()
    }

    override fun saveUserAvatarUrl(avatar: String) {
        userDataProvider.saveUserAvatarUrl(avatar)
    }

    override fun getUserAvatarUrl(): String {
        return userDataProvider.getUserAvatarUrl()
    }

    override fun clearUserAvatarUrl() {
        userDataProvider.clearUserAvatarUrl()
    }

    override fun saveUserGalleryPhotos(photoURLs: Set<String>) {
        userDataProvider.saveUserGalleryPhotos(photoURLs)
    }

    override fun getUserGalleryPhotos(): Set<String> {
        return userDataProvider.getUserGalleryPhotos()
    }
}