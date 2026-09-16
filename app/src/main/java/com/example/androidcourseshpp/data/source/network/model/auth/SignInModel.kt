package com.example.androidcourseshpp.data.source.network.model.auth

import com.example.androidcourseshpp.data.source.network.model.UserModel
import com.google.gson.annotations.SerializedName

data class SignInRequestModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class SignInResponseModel(
    @SerializedName("user")
    val user: UserModel,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)