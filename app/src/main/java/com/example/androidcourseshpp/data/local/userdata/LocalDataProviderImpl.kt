package com.example.androidcourseshpp.data.local.userdata

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.androidcourseshpp.data.common.BasePreferencesProvider
import com.example.androidcourseshpp.di.UserInfoPreferences
import javax.inject.Inject


private object UserInfoPreferencesKeys {
    val USER_AVATAR_URL = stringPreferencesKey("userAvatar")
    val USER_SERVER_ID = longPreferencesKey("userServerId")
    val USER_REMEMBER_STATE = booleanPreferencesKey("userSaveState")
    val USER_PHOTOS_URLS = stringSetPreferencesKey("userPhotos")
}

class LocalDataProviderImpl @Inject constructor(
    @param:UserInfoPreferences private val userInfoPreferences: DataStore<Preferences>
) : BasePreferencesProvider(userInfoPreferences), UserDataProvider, GalleryDataProvider {

    override suspend fun saveUserServerId(userServerId: Long) {
        savePreference(UserInfoPreferencesKeys.USER_SERVER_ID, userServerId)
    }

    override suspend fun getUserServerId(): Long? {
        return getPreference(UserInfoPreferencesKeys.USER_SERVER_ID)
    }

    override suspend fun clearUserServerId() {
        clearPreference(UserInfoPreferencesKeys.USER_SERVER_ID)
    }

    override suspend fun saveUserAvatarUrl(avatarUrl: String) {
        savePreference(UserInfoPreferencesKeys.USER_AVATAR_URL, avatarUrl)
    }

    override suspend fun getUserAvatarUrl(): String? {
        return getPreference(UserInfoPreferencesKeys.USER_AVATAR_URL)
    }

    override suspend fun clearUserAvatarUrl() {
        clearPreference(UserInfoPreferencesKeys.USER_AVATAR_URL)
    }

    override suspend fun isUserRemembered(): Boolean? {
        return getPreference(UserInfoPreferencesKeys.USER_REMEMBER_STATE)
    }

    override suspend fun setUserRememberState(toSaveUser: Boolean) {
        savePreference(UserInfoPreferencesKeys.USER_REMEMBER_STATE, toSaveUser)
    }

    override suspend fun clearUserRememberState() {
        clearPreference(UserInfoPreferencesKeys.USER_REMEMBER_STATE)
    }

    override suspend fun saveUserGalleryPhotos(photosURLs: Set<String>) {
        savePreference(UserInfoPreferencesKeys.USER_PHOTOS_URLS, photosURLs)
    }

    override suspend fun getUserGalleryPhotos(): Set<String>? {
        return getPreference(UserInfoPreferencesKeys.USER_PHOTOS_URLS)
    }

    override suspend fun clearGalleryPhotos() {
        return clearPreference(UserInfoPreferencesKeys.USER_PHOTOS_URLS)
    }
}
