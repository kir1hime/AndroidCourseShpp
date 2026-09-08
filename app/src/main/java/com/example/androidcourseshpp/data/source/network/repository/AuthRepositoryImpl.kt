package com.example.androidcourseshpp.data.source.network.repository

import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.data.source.network.mapper.toSignInRequestModel
import com.example.androidcourseshpp.data.source.network.mapper.toSignUpRequestModel
import com.example.androidcourseshpp.data.source.network.mapper.toUserInfo
import com.example.androidcourseshpp.data.source.network.service.auth.AuthService
import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.mapResult
import com.example.androidcourseshpp.domain.utils.onSuccess
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

    override suspend fun signIn(signInInfo: SignInInfo): Result<UserInfo, DataError.NetworkError> {

        val responseResult =
            authService.singIn(signInRequestModel = signInInfo.toSignInRequestModel())

        responseResult.onSuccess { data ->
            with(data) {
                jwtManager.saveTokens(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            }
        }

        return responseResult.mapResult { it.user.toUserInfo() }
    }


    override suspend fun singUp(signUpInfo: SignUpInfo): Result<UserInfo, DataError.NetworkError> {
        val responseResult =
            authService.signUp(signUpRequestModel = signUpInfo.toSignUpRequestModel(imageConverter))

        responseResult.onSuccess { data ->
            with(data) {
                jwtManager.saveTokens(accessToken, refreshToken)
            }
        }

        return responseResult.mapResult { it.user.toUserInfo() }
    }
}