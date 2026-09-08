package com.example.androidcourseshpp.data.source.network.dto.auth

import com.google.gson.annotations.SerializedName

data class TokenRefreshResponseDTO(
    @SerializedName("data")
    val data: TokenRefreshResponseDataDTO
)

data class TokenRefreshResponseDataDTO(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)