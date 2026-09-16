package com.example.androidcourseshpp.data.network.dto.auth

import com.example.androidcourseshpp.data.network.dto.common.UserDTO
import com.google.gson.annotations.SerializedName

data class SignUpResponseDTO(
    @SerializedName("data")
    val data: SignUpResponseDataDTO
)

data class SignUpResponseDataDTO(
    @SerializedName("user")
    val user: UserDTO,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)



