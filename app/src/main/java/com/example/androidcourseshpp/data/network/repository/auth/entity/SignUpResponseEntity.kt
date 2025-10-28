package com.example.androidcourseshpp.data.network.repository.auth.entity

import com.example.androidcourseshpp.data.network.dto.entity.User

data class SignUpResponseEntity(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)

