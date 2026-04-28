package com.example.androidcourseshpp.data.network.entity.signin

import com.example.androidcourseshpp.data.network.entity.User

data class SignInResponseEntity(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)