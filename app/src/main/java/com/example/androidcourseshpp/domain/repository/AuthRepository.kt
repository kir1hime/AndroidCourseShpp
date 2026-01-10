package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.auth.SignInInfo

interface AuthRepository {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearTokens()

    suspend fun signIn(signInInfo: SignInInfo) : Int
}