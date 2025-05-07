package com.example.androidcourseshpp.data.dataProvider

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.androidcourseshpp.data.EMAIL_KEY
import com.example.androidcourseshpp.data.PASSWORD_KEY


class DataProvider(val context: Context) {

    private val sharedPref: SharedPreferences by lazy {
        context.getSharedPreferences(USER_INFO_STORE, MODE_PRIVATE)
    }

    private val editor = sharedPref.edit()

    private companion object {
        const val USER_INFO_STORE = "userInfo"
    }

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

    fun deleteUserInfo(){
        deleteStringData(EMAIL_KEY)
        deleteStringData(PASSWORD_KEY)
    }

    fun saveUserInfo(eMail: String, password: String){
        putStringData(EMAIL_KEY, eMail)
        putStringData(PASSWORD_KEY, password)
    }

    fun getUserEMail(): String{
        return getStringData(EMAIL_KEY)
    }

}