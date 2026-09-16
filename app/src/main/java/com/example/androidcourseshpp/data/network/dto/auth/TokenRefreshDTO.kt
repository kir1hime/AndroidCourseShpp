package com.example.androidcourseshpp.data.network.dto.auth

import com.example.androidcourseshpp.data.network.model.auth.TokenRefreshResponseModel
import com.google.gson.annotations.SerializedName

data class TokenRefreshResponseDTO(
    @SerializedName("data")
    val data: TokenRefreshResponseModel
)