package com.example.androidcourseshpp.data.network.dto.refresh

import okio.Closeable

data class RefreshTokenResponseDTO(
    val accessToken: String,
    val refreshToken: String
)

data class RefreshTokenRequestDTO(val refreshToken: String)