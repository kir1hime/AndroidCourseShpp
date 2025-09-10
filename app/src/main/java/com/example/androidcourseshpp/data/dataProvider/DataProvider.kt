package com.example.androidcourseshpp.data.dataProvider

import android.content.SharedPreferences
import javax.inject.Inject

const val USER_INFO_STORE = "userInfo"
const val EMAIL_KEY = "userEMail"
const val PASSWORD_KEY = "userPassword"

class DataProvider @Inject constructor(private val sharedPref : SharedPreferences) {

    private val editor = sharedPref.edit()

    private fun getStringData(key: String): String {
        return sharedPref.getString(key, "").toString()
    }

    private fun deleteStringData(key: String) {
        editor.putString(key, "")
        editor.apply()
    }

    private fun putStringData(key: String, data: String) {
        editor.putString(key, data)
        editor.apply()
    }

    fun deleteUserInfo() {
        deleteStringData(EMAIL_KEY)
        deleteStringData(PASSWORD_KEY)
    }

    fun saveUserInfo(eMail: String, password: String) {
        putStringData(EMAIL_KEY, eMail)
        putStringData(PASSWORD_KEY, password)
    }

    fun getUserEMail(): String {
        return getStringData(EMAIL_KEY)
    }

}