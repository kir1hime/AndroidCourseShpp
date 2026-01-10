package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo

interface AuthRepository {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearTokens()

    suspend fun signIn(signInInfo: SignInInfo): Int
    suspend fun singUp(signUpInfo: SignUpInfo): Int
}