package com.example.androidcourseshpp.data.source.local.userdata

import android.content.SharedPreferences
import com.example.androidcourseshpp.di.DataProviderPref
import javax.inject.Inject

const val USER_INFO_STORE = "userInfo"
const val USER_AVATAR_URL = "userAvatar"
const val USER_SERVER_ID = "userServerId"
const val USER_PHOTOS = "userPhotos"
const val DB_DATA_VALIDITY = "databaseValidity"
const val DEFAULT_ID_VALUE: Int = -1
const val DEFAULT_AVATAR_VALUE = ""
const val DEFAULT_DB_DATA_VALUE = false


class LocalDataProvider @Inject constructor(@param:DataProviderPref private val sharedPref: SharedPreferences) :
    UserDataProvider, GalleryDataProvider, DatabaseValidityProvider {

    private val editor = sharedPref.edit()

    override fun saveUserServerId(userServerId: Int) {
        editor.putLong(USER_SERVER_ID, userServerId.toLong()).apply()
    }

    override fun getUserServerId() =
        sharedPref.getLong(USER_SERVER_ID, DEFAULT_ID_VALUE.toLong()).toInt()

    override fun clearUserServerId() {
        editor.putLong(USER_SERVER_ID, DEFAULT_ID_VALUE.toLong()).apply()
    }

    override fun saveUserAvatarUrl(avatar: String) {
        editor.putString(USER_AVATAR_URL, avatar).apply()
    }

    override fun getUserAvatarUrl() =
        sharedPref.getString(USER_AVATAR_URL, DEFAULT_AVATAR_VALUE) ?: DEFAULT_AVATAR_VALUE

    override fun clearUserAvatarUrl() {
        editor.putString(USER_AVATAR_URL, DEFAULT_AVATAR_VALUE).apply()
    }

    override fun saveUserGalleryPhotos(photoURLs: Set<String>) {
        editor.putStringSet(USER_PHOTOS, photoURLs).apply()
    }

    override fun getUserGalleryPhotos() =
        sharedPref.getStringSet(USER_PHOTOS, emptySet<String>()) ?: emptySet<String>()

    override fun clearGalleryPhotos() {
        editor.putStringSet(USER_PHOTOS, emptySet<String>())
    }

    override fun isDataValid(): Boolean =
        sharedPref.getBoolean(DB_DATA_VALIDITY, DEFAULT_DB_DATA_VALUE)


    override fun setDataValidity(isDataValid: Boolean) {
      editor.putBoolean(DB_DATA_VALIDITY, isDataValid).apply()
    }
}