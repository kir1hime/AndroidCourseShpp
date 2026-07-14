package com.example.androidcourseshpp.data.source.local.userdata

import android.content.SharedPreferences
import com.example.androidcourseshpp.di.DataProviderPref
import javax.inject.Inject

const val USER_INFO_STORE = "userInfo"
const val USER_AVATAR_URL = "userAvatar"
const val USER_SERVER_ID = "userServerId"
const val USER_REMEMBER_STATE = "userSaveState"
const val USER_PHOTOS = "userPhotos"
const val DATABASE_SYNC = "databaseSync"

const val DEFAULT_ID_VALUE: Long = -1L
const val DEFAULT_AVATAR_VALUE = ""
const val DEFAULT_USER_REMEMBER_STATE_VALUE = false


class LocalDataProviderImpl @Inject constructor(@param:DataProviderPref private val sharedPref: SharedPreferences) :
    UserDataProvider, GalleryDataProvider {

    private val editor = sharedPref.edit()

    override fun saveUserServerId(userServerId: Long) {
        editor.putLong(USER_SERVER_ID, userServerId).apply()
    }

    override fun getUserServerId() =
        sharedPref.getLong(USER_SERVER_ID, DEFAULT_ID_VALUE)

    override fun clearUserServerId() {
        editor.putLong(USER_SERVER_ID, DEFAULT_ID_VALUE).apply()
    }

    override fun saveUserAvatarUrl(avatar: String) {
        editor.putString(USER_AVATAR_URL, avatar).apply()
    }

    override fun getUserAvatarUrl() =
        sharedPref.getString(USER_AVATAR_URL, DEFAULT_AVATAR_VALUE) ?: DEFAULT_AVATAR_VALUE

    override fun clearUserAvatarUrl() {
        editor.putString(USER_AVATAR_URL, DEFAULT_AVATAR_VALUE).apply()
    }

    override fun isUserRemembered(): Boolean =
        sharedPref.getBoolean(USER_REMEMBER_STATE, DEFAULT_USER_REMEMBER_STATE_VALUE)


    override fun setUserRememberState(toSaveUser: Boolean) {
        editor.putBoolean(USER_REMEMBER_STATE, toSaveUser).apply()
    }

    override fun saveUserGalleryPhotos(photoURLs: Set<String>) {
        editor.putStringSet(USER_PHOTOS, photoURLs).apply()
    }

    override fun getUserGalleryPhotos() =
        sharedPref.getStringSet(USER_PHOTOS, emptySet<String>()) ?: emptySet<String>()

    override fun clearGalleryPhotos() {
        editor.putStringSet(USER_PHOTOS, emptySet<String>()).apply()
    }

    override fun isDatabaseSynced() =
        sharedPref.getBoolean(DATABASE_SYNC, false)

    override fun markDatabaseAsSynced(isSynced: Boolean) {
        editor.putBoolean(DATABASE_SYNC, isSynced).apply()
    }
}
