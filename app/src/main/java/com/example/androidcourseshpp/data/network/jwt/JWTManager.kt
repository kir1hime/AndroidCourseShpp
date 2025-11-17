package com.example.androidcourseshpp.data.network.jwt

interface JWTManager {
    fun getAccessToken(): String?
    fun saveAccessToken(token: String)
    fun getRefreshToken(): String?
    fun saveRefreshToken(token: String)
    fun saveTokens(accessToken: String, refreshToken: String)
    fun clearTokens()
}