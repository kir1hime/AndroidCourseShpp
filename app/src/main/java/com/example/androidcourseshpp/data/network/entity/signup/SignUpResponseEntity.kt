package com.example.androidcourseshpp.data.network.entity.signup

import com.example.androidcourseshpp.data.network.entity.User

data class SignUpResponseEntity(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)