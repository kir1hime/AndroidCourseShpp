package com.example.androidcourseshpp.data.network.webapi.tokenrefresh

import com.example.androidcourseshpp.data.network.dto.tokenrefresh.TokenRefreshResponseDTO

import retrofit2.http.POST

interface TokenRefreshAPI {

    @POST("refresh")
    fun refreshToken(): retrofit2.Call<TokenRefreshResponseDTO>
}