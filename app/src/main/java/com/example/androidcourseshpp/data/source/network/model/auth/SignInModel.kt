package com.example.androidcourseshpp.data.source.network.model.auth

import com.example.androidcourseshpp.data.source.network.model.common.UserModel

data class SignInRequestModel(
    val email: String,
    val password: String
)

data class SignInResponseModel(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String
)