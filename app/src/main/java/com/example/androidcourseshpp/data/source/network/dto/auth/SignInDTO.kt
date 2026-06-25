package com.example.androidcourseshpp.data.source.network.dto.auth

import com.google.gson.annotations.SerializedName
import com.example.androidcourseshpp.data.source.network.model.auth.SignInResponseModel

class SignInRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

class SignInResponseDTO(
    @SerializedName("data")
    val data: SignInResponseModel
)
