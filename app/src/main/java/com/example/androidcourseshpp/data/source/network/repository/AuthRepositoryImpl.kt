package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.network.model.auth.SignInData
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpData
import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.ui.utils.ImageConvertor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val jwtManager: JWTManager,
    private val servicesProvider: ServicesProvider,
    private val imageConvertor: ImageConvertor
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

    override suspend fun signIn(signInInfo: SignInInfo): UserInfo {
        val data = SignInData(email = signInInfo.email, password = signInInfo.password)

        val response = servicesProvider.getAuthService()
            .singIn(data)

        jwtManager.saveTokens(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken
        )

        return response.user.toUserInfo()
    }

    override suspend fun singUp(signUpInfo: SignUpInfo): UserInfo {
        val data = SignUpData(
            userName = signUpInfo.userName,
            mobilePhone = signUpInfo.mobilePhone,
            email = signUpInfo.email,
            password = signUpInfo.password,
            image = imageConvertor.convertBitmapToMultipartBody(signUpInfo.avatar)
        )
        val response = servicesProvider.getAuthService().signUp(data)

        jwtManager.saveTokens(response.accessToken, response.refreshToken)

        return response.user.toUserInfo()
    }
}