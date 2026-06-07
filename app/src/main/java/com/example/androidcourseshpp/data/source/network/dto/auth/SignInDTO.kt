package com.example.androidcourseshpp.data.source.network.dto.auth

import com.example.androidcourseshpp.data.source.network.model.auth.SignInResponseModel

class SignInRequestDTO(
    val email: String,
    val password: String
)

class SignInResponseDTO(val data: SignInResponseModel)
