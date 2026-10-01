package com.example.androidcourseshpp.data.network.jwt

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.androidcourseshpp.data.common.BasePreferencesProvider
import com.example.androidcourseshpp.di.JWTManagerPreferences
import javax.inject.Inject

private object JWTPreferencesKeys {
    val ACCESS_TOKEN = stringPreferencesKey("accessToken")
    val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
}

class JWTManagerImpl @Inject constructor(
    @param:JWTManagerPreferences private val jwtPreferences: DataStore<Preferences>
) : BasePreferencesProvider(jwtPreferences), JWTManager {

    override suspend fun getAccessToken(): String? {
        return getPreference(JWTPreferencesKeys.ACCESS_TOKEN)
    }

    override suspend fun getRefreshToken(): String? {
        return getPreference(JWTPreferencesKeys.REFRESH_TOKEN)
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        savePreference(JWTPreferencesKeys.ACCESS_TOKEN, accessToken)
        savePreference(JWTPreferencesKeys.REFRESH_TOKEN, refreshToken)
    }

    override suspend fun clearTokens() {
        clearPreference(JWTPreferencesKeys.ACCESS_TOKEN)
        clearPreference(JWTPreferencesKeys.REFRESH_TOKEN)
    }
}