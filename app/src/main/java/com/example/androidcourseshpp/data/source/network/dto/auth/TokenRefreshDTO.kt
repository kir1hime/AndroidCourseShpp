package com.example.androidcourseshpp.data.source.network.dto.auth

import com.example.androidcourseshpp.data.source.network.model.auth.TokenRefreshResponseModel
import com.google.gson.annotations.SerializedName

data class TokenRefreshResponseDTO(
    @SerializedName("data")
    val data: TokenRefreshResponseModel
)