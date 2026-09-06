package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.data.source.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpRequestModel
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
    private val imageConvertor: ImageConverter
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
        val data = SignInRequestModel(email = signInInfo.email, password = signInInfo.password)

        val response = authService.singIn(data)

        jwtManager.saveTokens(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken
        )

        response.user.toUserInfo()
    }

    override suspend fun singUp(signUpInfo: SignUpInfo): Result<UserInfo> = wrapNetworkExceptions {
        val data = SignUpRequestModel(
            userName = signUpInfo.userName,
            mobilePhone = signUpInfo.mobilePhone,
            email = signUpInfo.email,
            password = signUpInfo.password,
            image = imageConvertor.convertBitmapToMultipartBody(signUpInfo.avatar)
        )
        val response = authService.signUp(data)

        jwtManager.saveTokens(response.accessToken, response.refreshToken)

        response.user.toUserInfo()
    }
}