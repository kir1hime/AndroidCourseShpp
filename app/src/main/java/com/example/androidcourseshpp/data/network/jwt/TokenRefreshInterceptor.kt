package com.example.androidcourseshpp.data.network.jwt

import com.example.androidcourseshpp.data.network.utils.HEADER_VALUE_TYPE
import com.example.androidcourseshpp.data.network.utils.REFRESH_HEADER_NAME
import com.example.androidcourseshpp.data.network.utils.addAuthHeaderForRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRefreshInterceptor @Inject constructor(
    private val jwtManager: JWTManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        chain.addAuthHeaderForRequest(
            token = jwtManager.getRefreshToken(),
            headerName = REFRESH_HEADER_NAME,
            headerValueType = HEADER_VALUE_TYPE
        )
    }
}