package com.example.androidcourseshpp.data.source.network.model.auth

import com.example.androidcourseshpp.data.source.network.model.UserModel
import com.google.gson.annotations.SerializedName

data class SignUpResponseModel(
    @SerializedName("user")
    val user: UserModel,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)