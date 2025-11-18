package com.example.androidcourseshpp.data.network.webapi.tokenrefresh

import com.example.androidcourseshpp.data.network.dto.tokenrefresh.TokenRefreshResponseDTO
import retrofit2.Response
import retrofit2.http.POST

interface TokenRefreshAPI {

    @POST
    suspend fun refreshToken(): Response<TokenRefreshResponseDTO>
}