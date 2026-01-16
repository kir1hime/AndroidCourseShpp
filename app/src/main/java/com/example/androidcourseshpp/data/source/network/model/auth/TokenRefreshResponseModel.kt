package com.example.androidcourseshpp.data.source.network.entity.auth

data class TokenRefreshResponseModel(
    val accessToken: String,
    val refreshToken: String
)