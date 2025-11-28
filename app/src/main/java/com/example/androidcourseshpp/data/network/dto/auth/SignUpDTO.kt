package com.example.androidcourseshpp.data.network.dto.auth

import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpResponseEntity

data class SignUpRequestDTO(
    val email: String,
    val password: String,
    val name: String,
    val phone: String,
)

data class SignUpResponseDTO(
    val data: SignUpResponseEntity
)



