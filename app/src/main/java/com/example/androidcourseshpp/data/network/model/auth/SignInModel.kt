package com.example.androidcourseshpp.data.network.model.auth

import com.example.androidcourseshpp.data.network.model.common.UserModel

data class SignInRequestModel(
    val email: String,
    val password: String
)

data class SignInResponseModel(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String
)