package com.example.androidcourseshpp.data.network.dto.auth

import com.example.androidcourseshpp.data.network.entity.signin.SignInResponseEntity

class SignInRequestDTO(
    val email: String,
    val password: String
)

class SignInResponseDTO(val data: SignInResponseEntity)