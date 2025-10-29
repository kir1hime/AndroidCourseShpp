package com.example.androidcourseshpp.data.network.jwt

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton
import com.example.androidcourseshpp.di.JWTManagerPref

const val JWT_STORAGE = "jwtStorage"
const val ACCESS_TOKEN = "accessToken"
const val REFRESH_TOKEN = "refreshToken"

@Singleton
class JWTManagerImpl @Inject constructor(@JWTManagerPref private val sharedPref: SharedPreferences): JWTManager{

    private val editor = sharedPref.edit()

    override  fun getAccessToken() =
        sharedPref.getString(ACCESS_TOKEN, null)


    override  fun saveAccessToken(token: String) {
        editor.putString(ACCESS_TOKEN, token).apply()
    }

    override  fun getRefreshToken() =
        sharedPref.getString(REFRESH_TOKEN, null)

    override  fun saveRefreshToken(token: String) {
        editor.putString(REFRESH_TOKEN, token).apply()
    }
}