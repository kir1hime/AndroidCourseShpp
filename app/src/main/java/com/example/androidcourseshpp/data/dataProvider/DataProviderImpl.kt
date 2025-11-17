package com.example.androidcourseshpp.data.dataProvider

import android.content.SharedPreferences
import com.example.androidcourseshpp.di.DataProviderPref
import javax.inject.Inject

const val USER_INFO_STORE = "userInfo"
const val USER_SERVER_ID = "userServerId"
const val DEFAULT_ID_VALUE: Long = -1


class DataProviderImpl @Inject constructor(@DataProviderPref private val sharedPref: SharedPreferences) :
    DataProvider {

    private val editor = sharedPref.edit()

    override fun saveUserServerId(userServerId: Long) {
        editor.putLong(USER_SERVER_ID, userServerId).apply()
    }

    override fun getUserServerId() =
        sharedPref.getLong(USER_SERVER_ID, DEFAULT_ID_VALUE)

    override fun clearUserServerId() {
        editor.putLong(USER_SERVER_ID, DEFAULT_ID_VALUE).apply()
    }
}