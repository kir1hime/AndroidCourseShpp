package com.example.androidcourseshpp.data.network.jwt

import com.example.androidcourseshpp.data.network.webapi.tokenrefresh.TokenRefreshAPI
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenRefreshAPI: TokenRefreshAPI,
    private val jwtManager: JWTManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {

        val newTokensResponse = tokenRefreshAPI.refreshToken()

        if (!newTokensResponse.isSuccessful) {
            throw AuthenticationException()
        }
        val newTokens = newTokensResponse.body()?.data ?: throw AuthenticationException()

        jwtManager.saveTokens(newTokens.accessToken, newTokens.refreshToken)
        return@runBlocking response.request.newBuilder()
            .header("Authorization", "Bearer $newTokens").build()

    }


}

class AuthenticationException() : Exception() {
}