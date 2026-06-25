package com.example.androidcourseshpp.data.source.network.jwt

interface JWTManager {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveTokens(accessToken: String?, refreshToken: String?)
    fun clearTokens()
}