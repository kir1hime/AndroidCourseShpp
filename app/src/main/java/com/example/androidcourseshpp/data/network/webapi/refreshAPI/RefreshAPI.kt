package com.example.androidcourseshpp.data.network.webapi.refreshAPI

import com.example.androidcourseshpp.data.network.dto.refresh.RefreshTokenRequestDTO
import com.example.androidcourseshpp.data.network.dto.refresh.RefreshTokenResponseDTO
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST

interface RefreshAPI {

    @POST("refresh")
    suspend fun refreshTokens(@Body refreshTokenRequestDTO: RefreshTokenRequestDTO): Response<RefreshTokenResponseDTO>
}