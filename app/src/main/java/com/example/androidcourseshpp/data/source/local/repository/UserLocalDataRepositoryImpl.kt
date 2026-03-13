package com.example.androidcourseshpp.data.source.local.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataRepositoryImpl @Inject constructor(private val userDataProvider: UserDataProvider) :
    UserLocalDataRepository {
    override fun saveUserServerId(userServerId: Int) {
        userDataProvider.saveUserServerId(userServerId)
    }

    override fun getUserServerId() = userDataProvider.getUserServerId()


    override fun clearUserServerId() {
        userDataProvider.clearUserServerId()
    }

    override fun saveUserAvatarUrl(avatar: String) {
        userDataProvider.saveUserAvatarUrl(avatar)
    }

    override fun getUserAvatarUrl() = userDataProvider.getUserAvatarUrl()


    override fun clearUserAvatarUrl() {
        userDataProvider.clearUserAvatarUrl()
    }

    override fun isUserRemembered() = userDataProvider.isUserRemembered()


    override fun setUserRememberState(toSaveUser: Boolean) {
        userDataProvider.setUserRememberState(toSaveUser)
    }
}