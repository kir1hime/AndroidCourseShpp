package com.example.androidcourseshpp.data.dataProvider

import android.content.SharedPreferences
import com.example.androidcourseshpp.di.DataProviderPref
import javax.inject.Inject
import javax.inject.Singleton

const val USER_INFO_STORE = "userInfo"
const val USER_SERVER_ID = "userServerId"
const val DEFAULT_USER_SERVER_ID_VALUE: Long = -1

@Singleton
class UserDataProviderImpl @Inject constructor(@DataProviderPref private val sharedPref: SharedPreferences) :
    UserDataProvider {

    private val editor = sharedPref.edit()

    override fun getUserServerId() =
        sharedPref.getLong(USER_SERVER_ID, DEFAULT_USER_SERVER_ID_VALUE)

    override fun saveUserServerId(userServerId: Long) {
        editor.putLong(USER_SERVER_ID, userServerId).apply()
    }

    override fun clearUserServerId() {
        editor.putLong(USER_SERVER_ID, DEFAULT_USER_SERVER_ID_VALUE).apply()
    }
}