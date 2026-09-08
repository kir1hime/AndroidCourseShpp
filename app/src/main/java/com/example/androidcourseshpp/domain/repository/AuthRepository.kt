package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface AuthRepository {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearTokens()
    suspend fun signIn(signInInfo: SignInInfo): Result<UserInfo, DataError.NetworkError>
    suspend fun singUp(signUpInfo: SignUpInfo): Result<UserInfo, DataError.NetworkError>
}