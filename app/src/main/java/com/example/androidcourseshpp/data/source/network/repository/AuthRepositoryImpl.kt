package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.network.entity.auth.SignInData
import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.data.source.network.service.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val jwtManager: JWTManager,
    private val serviceProviderHolder: RetrofitServiceProviderHolder
) : AuthRepository {
    override fun getAccessToken(): String? {
        return jwtManager.getAccessToken()
    }

    override fun getRefreshToken(): String? {
        return jwtManager.getRefreshToken()
    }

    override fun clearTokens() {
        jwtManager.clearTokens()
    }

    override suspend fun signIn(signInInfo: SignInInfo): Int {
        val data = SignInData(email = signInInfo.email, password = signInInfo.password)

        val response = serviceProviderHolder.serviceProvider.getAuthService()
            .singIn(data)

        jwtManager.saveTokens(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken
        )

        return response.user.id
    }
}