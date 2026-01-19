package com.example.androidcourseshpp.data.source.network.model.auth

import com.example.androidcourseshpp.data.source.network.model.UserModel

data class SignInResponseEntity(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String
)