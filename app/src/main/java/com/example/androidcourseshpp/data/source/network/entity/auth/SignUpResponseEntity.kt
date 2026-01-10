package com.example.androidcourseshpp.data.source.network.entity.auth

import com.example.androidcourseshpp.data.source.network.entity.User

data class SignUpResponseEntity(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)