package com.example.androidcourseshpp.data.network.dto.user

import com.example.androidcourseshpp.data.network.dto.entity.User

data class UpdateUserRequestDTO(
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?
)

data class UpdateUserResponseDTO(val user: User)

