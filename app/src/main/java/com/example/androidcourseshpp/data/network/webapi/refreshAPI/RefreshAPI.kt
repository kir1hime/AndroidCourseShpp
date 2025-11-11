package com.example.androidcourseshpp.data.network.webapi.refreshAPI

import com.example.androidcourseshpp.data.network.dto.refresh.RefreshTokenResponseDTO
import retrofit2.Response
import retrofit2.http.Header


import retrofit2.http.POST

interface RefreshAPI {

    @POST("refresh")
    suspend fun refreshTokens(@Header("RefreshToken") token: String): Response<RefreshTokenResponseDTO>
}