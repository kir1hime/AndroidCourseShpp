package com.example.androidcourseshpp.data.network.dto.auth

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