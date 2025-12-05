package com.example.androidcourseshpp.data.network.api.token

import com.example.androidcourseshpp.data.network.dto.token.TokenRefreshResponseDTO
import retrofit2.Call

import retrofit2.http.POST

interface TokenRefreshAPI {

    @POST("refresh")
    fun refreshToken(): Call<TokenRefreshResponseDTO>
}