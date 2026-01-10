package com.example.androidcourseshpp.data.source.network.entity.auth

data class TokenRefreshResponseEntity(
    val accessToken: String,
    val refreshToken: String
)