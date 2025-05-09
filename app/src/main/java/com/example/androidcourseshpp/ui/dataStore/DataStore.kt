package com.example.androidcourseshpp.ui.dataStore

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class DataStore(val context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(USER_INFO_STORE, MODE_PRIVATE)

    private val editor = sharedPref.edit()

    private companion object {
        const val USER_INFO_STORE = "userInfo"
    }

    fun getStringData(key: String): String {
        return sharedPref.getString(key, "").toString()
    }

    fun putStringData(key: String, data: String) {
        editor.putString(key, data)
        editor.apply()
    }

    fun deleteStringData(key: String) {
        editor.putString(key, "")
        editor.apply()
    }

}