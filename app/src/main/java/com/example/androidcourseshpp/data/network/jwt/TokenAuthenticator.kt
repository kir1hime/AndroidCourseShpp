package com.example.androidcourseshpp.data.network.jwt

import com.example.androidcourseshpp.data.local.userdata.GalleryDataProvider
import com.example.androidcourseshpp.data.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.network.api.auth.TokenRefreshAPI
import com.example.androidcourseshpp.data.network.utils.AUTH_HEADER_NAME
import com.example.androidcourseshpp.data.network.utils.HEADER_VALUE_TYPE
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
    private val userDataProvider: UserDataProvider,
    private val galleryDataProvider: GalleryDataProvider
) : Authenticator {

    private val lock = Any()

    override fun authenticate(route: Route?, response: Response): Request? {

        return synchronized(lock) {
            runBlocking {
                if (countNumberOfResponses(response) >= MAX_NUM_OF_RESPONSES) {
                    return@runBlocking null
                }
                val currentAccessToken = jwtManager.getAccessToken()

                if (currentAccessToken != response.request
                        .header(AUTH_HEADER_NAME)?.removePrefix("$HEADER_VALUE_TYPE ")
                ) {
                    return@runBlocking response.request.newBuilder()
                        .header(AUTH_HEADER_NAME, "$HEADER_VALUE_TYPE $currentAccessToken")
                        .build()
                }


                val newTokensResponse = tokenRefreshAPI.refreshToken().execute()

                if (!newTokensResponse.isSuccessful) {

                    jwtManager.clearTokens()
                    userDataProvider.clearUserServerId()
                    userDataProvider.clearUserAvatarUrl()
                    galleryDataProvider.clearGalleryPhotos()
                    contactsLocalRepository.clearContacts()
                    userDataProvider.clearUserRememberState()

                    return@runBlocking null
                }
                val newTokens = newTokensResponse.body()?.data ?: return@runBlocking null

                jwtManager.saveTokens(newTokens.accessToken, newTokens.refreshToken)

                return@runBlocking response.request.newBuilder()
                    .header(AUTH_HEADER_NAME, "$HEADER_VALUE_TYPE ${newTokens.accessToken}").build()
            }
        }
    }

    private fun countNumberOfResponses(response: Response): Int {
        var responseCounter = 0

        var currentResponse: Response? = response

        while (currentResponse != null) {
            responseCounter++
            currentResponse = currentResponse.priorResponse
        }
        return responseCounter
    }

    companion object {

        const val MAX_NUM_OF_RESPONSES = 3
    }
}

