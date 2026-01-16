package com.example.androidcourseshpp.data.source.network.entity.auth

import com.example.androidcourseshpp.data.source.network.entity.UserModel

data class SignInResponseEntity(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String
)