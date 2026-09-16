package com.example.androidcourseshpp.data.source.network.dto.user

import com.example.androidcourseshpp.data.source.network.dto.common.UserDTO
import com.google.gson.annotations.SerializedName

data class GetUserResponseDTO(
    @SerializedName("data")
    val data: GetUserResponseDataDTO
)

data class GetUserResponseDataDTO(
    @SerializedName("user")
    val user: UserDTO
)
