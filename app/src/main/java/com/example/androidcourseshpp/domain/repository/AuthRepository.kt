package com.example.androidcourseshpp.domain.repository

interface AuthRepository {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveTokens(accessToken: String?, refreshToken: String?)
    fun clearTokens()
}