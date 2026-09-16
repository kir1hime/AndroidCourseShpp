package com.example.androidcourseshpp.data.network.api.auth

import com.example.androidcourseshpp.data.network.dto.auth.TokenRefreshResponseDTO
import retrofit2.Call
import retrofit2.http.POST

interface TokenRefreshAPI {

    @POST("refresh")
    fun refreshToken(): Call<TokenRefreshResponseDTO>
}