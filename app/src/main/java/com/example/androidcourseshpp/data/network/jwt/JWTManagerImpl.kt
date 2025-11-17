package com.example.androidcourseshpp.data.network.jwt

import android.content.SharedPreferences
import javax.inject.Inject
import com.example.androidcourseshpp.di.JWTManagerPref

const val JWT_STORAGE = "jwtStorage"
const val ACCESS_TOKEN = "accessToken"
const val REFRESH_TOKEN = "refreshToken"


class JWTManagerImpl @Inject constructor(@JWTManagerPref private val sharedPref: SharedPreferences): JWTManager{

    private val editor = sharedPref.edit()

    override  fun getAccessToken() =
        sharedPref.getString(ACCESS_TOKEN, "")


    override  fun saveAccessToken(token: String) {
        editor.putString(ACCESS_TOKEN, token).apply()
    }

    override  fun getRefreshToken() =
        sharedPref.getString(REFRESH_TOKEN, "")

    override  fun saveRefreshToken(token: String) {
        editor.putString(REFRESH_TOKEN, token).apply()
    }

    override fun saveTokens(accessToken: String, refreshToken: String) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
    }

    override fun clearTokens() {
        saveTokens("", "")
    }
}