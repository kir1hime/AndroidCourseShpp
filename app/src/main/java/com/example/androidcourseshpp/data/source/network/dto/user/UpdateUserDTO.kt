package com.example.androidcourseshpp.data.source.network.dto.user

import com.example.androidcourseshpp.data.source.network.dto.common.UserDTO
import com.google.gson.annotations.SerializedName
import java.util.Date

data class UpdateUserRequestDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("career")
    val career: String?,
    @SerializedName("birthday")
    val birthday: Date?,
    @SerializedName("facebook")
    val facebook: String?,
    @SerializedName("instagram")
    val instagram: String?,
    @SerializedName("twitter")
    val twitter: String?,
    @SerializedName("linkedin")
    val linkedin: String?
)

data class UpdateUserResponseDTO(
    @SerializedName("user")
    val user: UserDTO
)

