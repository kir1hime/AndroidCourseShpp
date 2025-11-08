package com.example.androidcourseshpp.data.network.dto.auth

import com.example.androidcourseshpp.data.network.service.auth.entity.AuthResponseEntity

class SignInRequestDTO(
    val email: String,
    val password: String
)

class SignInResponseDTO(val data: AuthResponseEntity)