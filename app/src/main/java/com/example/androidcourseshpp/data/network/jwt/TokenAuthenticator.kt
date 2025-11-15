package com.example.androidcourseshpp.data.network.jwt

import com.example.androidcourseshpp.data.network.webapi.refreshAPI.RefreshAPI
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class TokenAuthenticator(
    private val refreshApi: RefreshAPI,
    private val jwtManager: JWTManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        return runBlocking {
            val currentRefreshToken = jwtManager.getRefreshToken() ?: return@runBlocking null


            val newTokensResponse = try {
                refreshApi.refreshTokens(currentRefreshToken)
            } catch (e: Exception) {
                return@runBlocking null
            }

            if (!newTokensResponse.isSuccessful) {
                return@runBlocking null
            }

            val newTokens = newTokensResponse.body()
            if (newTokens == null ||
                newTokens.accessToken == null ||
                newTokens.refreshToken == null
            ) {
                return@runBlocking null
            }

            jwtManager.saveAccessToken(newTokens.accessToken)
            jwtManager.saveRefreshToken(newTokens.refreshToken)

            response.request.newBuilder()
                .header("Authorization", "Bearer ${newTokens.accessToken}")
                .build()
        }
    }
}