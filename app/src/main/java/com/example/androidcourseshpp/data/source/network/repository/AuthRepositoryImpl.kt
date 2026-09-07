package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.data.source.network.mapper.toSignInRequestModel
import com.example.androidcourseshpp.data.source.network.mapper.toSignUpRequestModel
import com.example.androidcourseshpp.data.source.network.mapper.toUserInfo
import com.example.androidcourseshpp.data.source.network.service.auth.AuthService
import com.example.androidcourseshpp.data.source.network.utils.wrapNetworkExceptions
import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.ui.utils.imageconvertor.ImageConverter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val jwtManager: JWTManager,
    private val authService: AuthService,
    private val imageConverter: ImageConverter
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

    override suspend fun signIn(signInInfo: SignInInfo): Result<UserInfo> = wrapNetworkExceptions {
        val response = authService.singIn(signInInfo.toSignInRequestModel())

        jwtManager.saveTokens(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken
        )
        response.user.toUserInfo()
    }

    override suspend fun singUp(signUpInfo: SignUpInfo): Result<UserInfo> = wrapNetworkExceptions {
        val response = authService.signUp(signUpInfo.toSignUpRequestModel(imageConverter))

        jwtManager.saveTokens(response.accessToken, response.refreshToken)

        response.user.toUserInfo()
    }
}