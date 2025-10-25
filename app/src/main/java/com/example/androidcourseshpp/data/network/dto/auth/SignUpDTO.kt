package com.example.androidcourseshpp.data.network.dto.auth

data class SignUpRequestDTO(
    val email: String,
    val password: String,
    val name: String,
    val phone: String
)

data class SignUpResponseDTO(
    val data: SignUpDataResponse
)

data class SignUpDataResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)

data class User(
    val email: String,
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?,
    val image: String?
)

