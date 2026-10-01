package com.example.androidcourseshpp.data.local.repository

import com.example.androidcourseshpp.data.local.userdata.UserDataProvider
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataRepositoryImpl @Inject constructor(private val userDataProvider: UserDataProvider) :
    UserLocalDataRepository {
    override suspend fun saveUserServerId(userServerId: Long) {
        userDataProvider.saveUserServerId(userServerId)
    }

    override suspend fun clearUserServerId() {
        userDataProvider.clearUserServerId()
    }

    override suspend fun saveUserAvatarUrl(avatar: String) {
        userDataProvider.saveUserAvatarUrl(avatar)
    }

    override suspend fun getUserAvatarUrl() = userDataProvider.getUserAvatarUrl()


    override suspend fun clearUserAvatarUrl() {
        userDataProvider.clearUserAvatarUrl()
    }

    override suspend fun isUserRemembered() = userDataProvider.isUserRemembered()


    override suspend fun setUserRememberState(toSaveUser: Boolean) {
        userDataProvider.setUserRememberState(toSaveUser)
    }
}