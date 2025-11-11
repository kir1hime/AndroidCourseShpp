package com.example.androidcourseshpp.data.network.jwt

interface JWTManager {
     fun getAccessToken(): String?
     fun saveAccessToken(token: String)
     fun getRefreshToken() : String?
     fun saveRefreshToken(token: String)
     fun clearTokens()
}