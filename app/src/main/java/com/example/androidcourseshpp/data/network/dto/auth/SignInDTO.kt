package com.example.androidcourseshpp.data.network.dto.auth

import com.example.androidcourseshpp.data.network.model.auth.SignInResponseModel
import com.google.gson.annotations.SerializedName

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
