package com.example.androidcourseshpp.data.source.network.jwt

import android.content.SharedPreferences
import com.example.androidcourseshpp.di.JWTManagerPref
import javax.inject.Inject

const val JWT_STORAGE = "jwtStorage"
const val ACCESS_TOKEN = "accessToken"
const val REFRESH_TOKEN = "refreshToken"


class JWTManagerImpl @Inject constructor(@JWTManagerPref private val sharedPref: SharedPreferences) :
    JWTManager {

    private val editor = sharedPref.edit()

    override fun getAccessToken() =
        sharedPref.getString(ACCESS_TOKEN, null)


    private fun saveAccessToken(token: String?) {
        editor.putString(ACCESS_TOKEN, token).apply()
    }

    override fun getRefreshToken() =
        sharedPref.getString(REFRESH_TOKEN, null)

    private fun saveRefreshToken(token: String?) {
        editor.putString(REFRESH_TOKEN, token).apply()
    }

    override fun saveTokens(accessToken: String?, refreshToken: String?) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
    }

    override fun clearTokens() {
        saveTokens(null, null)
    }
}