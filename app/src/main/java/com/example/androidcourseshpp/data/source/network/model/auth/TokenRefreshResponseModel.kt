package com.example.androidcourseshpp.data.source.network.model.auth

data class TokenRefreshResponseModel(
    val accessToken: String,
    val refreshToken: String
)