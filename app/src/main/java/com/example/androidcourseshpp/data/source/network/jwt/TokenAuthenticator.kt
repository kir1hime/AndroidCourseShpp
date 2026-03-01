package com.example.androidcourseshpp.data.source.network.jwt

import com.example.androidcourseshpp.data.source.local.userdata.LocalDataProvider
import com.example.androidcourseshpp.data.source.network.api.auth.TokenRefreshAPI
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenRefreshAPI: TokenRefreshAPI,
    private val jwtManager: JWTManager,
    private val contactsLocalRepository: ContactsLocalRepository,
    private val localDataProvider: LocalDataProvider
) : Authenticator {

    private val lock = Any()

    override fun authenticate(route: Route?, response: Response): Request? {

        synchronized(lock) {
            if (countNumberOfResponses(response) >= MAX_NUM_OF_RESPONSES) {
                return null
            }
            val currentAccessToken = jwtManager.getAccessToken()

            if (currentAccessToken != response.request
                    .header("Authorization")?.removePrefix("Bearer ")
            ) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentAccessToken").build()
            }


            val newTokensResponse = tokenRefreshAPI.refreshToken().execute()

            if (!newTokensResponse.isSuccessful) {
                jwtManager.clearTokens()
                localDataProvider.clearUserServerId()
                localDataProvider.clearUserAvatarUrl()
                localDataProvider.clearGalleryPhotos()
                localDataProvider.markDatabaseAsSynced(false)
                runBlocking {
                    contactsLocalRepository.clearContacts()
                }

                return null
            }
            val newTokens = newTokensResponse.body()?.data ?: return null

            jwtManager.saveTokens(newTokens.accessToken, newTokens.refreshToken)

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${newTokens.accessToken}").build()
        }

    }

    private fun countNumberOfResponses(response: Response): Int {
        var responseCounter = DEFAULT_NUM_OF_RESPONSES

        var currentResponse: Response? = response

        while (currentResponse != null) {
            responseCounter++
            currentResponse = currentResponse.priorResponse

        }
        return responseCounter
    }

    companion object {
        const val DEFAULT_NUM_OF_RESPONSES = 0
        const val MAX_NUM_OF_RESPONSES = 3
    }
}
