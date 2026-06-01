package com.example.androidcourseshpp.data.source.local.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalRepositoryImpl @Inject constructor(private val userDataProvider: UserDataProvider) : UserLocalRepository {
    override fun saveUserServerId(userServerId: Long) {
        userDataProvider.saveUserServerId(userServerId)
    }

    override fun getUserServerId(): Long {
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
}