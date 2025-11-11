package com.example.androidcourseshpp.data.dataProvider

import android.content.SharedPreferences
import com.example.androidcourseshpp.di.DataProviderPref
import javax.inject.Inject
import javax.inject.Singleton

const val USER_INFO_STORE = "userInfo"
const val USER_NAME = "userName"

@Singleton
class UserDataProviderImpl @Inject constructor(@DataProviderPref private val sharedPref: SharedPreferences) :
    UserDataProvider {

    private val editor = sharedPref.edit()

    override fun getUserName() = sharedPref.getString(USER_NAME, null)


    override fun saveUserName(name: String) {
        editor.putString(USER_NAME, name).apply()
    }

    override fun clearUserName() {
        editor.putString(USER_NAME, null).apply()
    }
}