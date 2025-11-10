package com.example.androidcourseshpp.data.network.jwt

import com.example.androidcourseshpp.data.network.webapi.refreshAPI.RefreshAPI
import com.example.androidcourseshpp.data.network.dto.refresh.RefreshTokenRequestDTO
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route



class TokenAuthenticator (
    private val refreshApi: RefreshAPI,
    private val jwtManagerImpl: JWTManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        return runBlocking {
            val currentAccessToken = jwtManagerImpl.getAccessToken()
            val currentRefreshToken = jwtManagerImpl.getRefreshToken() ?: return@runBlocking null

            if (currentAccessToken != response.request.header("Authorization")
                    ?.removePrefix("Bearer ")
            ) {
                return@runBlocking response.request.newBuilder()
                    .header("Authorization", "Bearer $currentAccessToken")
                    .build()
            }

            val newTokensResponse =
                refreshApi.refreshTokens(RefreshTokenRequestDTO(currentRefreshToken))

            if (!newTokensResponse.isSuccessful) {
                return@runBlocking null
            }

            val newTokens = newTokensResponse.body() ?: return@runBlocking null

            jwtManagerImpl.saveAccessToken(newTokens.accessToken)
            jwtManagerImpl.saveRefreshToken(newTokens.refreshToken)

            response.request.newBuilder()
                .header("Authorization", "Bearer ${newTokens.accessToken}")
                .build()
        }
    }
}