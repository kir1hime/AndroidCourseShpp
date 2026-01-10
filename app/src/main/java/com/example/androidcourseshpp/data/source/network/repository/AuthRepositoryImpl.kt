package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.domain.repository.AuthRepository

class AuthRepositoryImpl(private val jwtManager: JWTManager) : AuthRepository {
    override fun getAccessToken(): String? {
        return jwtManager.getAccessToken()
    }

    override fun getRefreshToken(): String? {
        return jwtManager.getRefreshToken()
    }

    override fun saveTokens(accessToken: String?, refreshToken: String?) {
        jwtManager.saveTokens(accessToken, refreshToken)
    }

    override fun clearTokens() {
        jwtManager.clearTokens()
    }
}