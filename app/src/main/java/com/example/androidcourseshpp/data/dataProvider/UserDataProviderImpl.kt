package com.example.androidcourseshpp.data.dataProvider

import android.content.SharedPreferences
import com.example.androidcourseshpp.di.DataProviderPref
import javax.inject.Inject

const val USER_INFO_STORE = "userInfo"
const val USER_AVATAR_URL = "userAvatar"
const val USER_SERVER_ID = "userServerId"
const val DEFAULT_ID_VALUE: Int = -1
const val DEFAULT_AVATAR_VALUE = ""


class UserDataProviderImpl @Inject constructor(@DataProviderPref private val sharedPref: SharedPreferences) :
    UserDataProvider {

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
}