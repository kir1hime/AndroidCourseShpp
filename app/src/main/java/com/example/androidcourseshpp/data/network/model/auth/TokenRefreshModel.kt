package com.example.androidcourseshpp.data.network.model.auth

import com.google.gson.annotations.SerializedName

data class TokenRefreshResponseModel(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)