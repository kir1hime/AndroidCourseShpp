package com.example.androidcourseshpp.data.source.network.dto.auth

import com.example.androidcourseshpp.data.source.network.dto.common.UserDTO
import com.google.gson.annotations.SerializedName

data class SignInRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class SignInResponseDTO(
    @SerializedName("data")
    val data: SignInResponseDataDTO
)

data class SignInResponseDataDTO(
    @SerializedName("user")
    val user: UserDTO,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)




