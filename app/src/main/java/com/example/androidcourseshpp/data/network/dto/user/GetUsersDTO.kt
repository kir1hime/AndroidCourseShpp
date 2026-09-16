package com.example.androidcourseshpp.data.network.dto.user

import com.example.androidcourseshpp.data.network.dto.common.UserDTO
import com.google.gson.annotations.SerializedName


data class GetUsersResponseDTO(
    @SerializedName("data")
    val data: GetUsersResponseDataDTO
)

class GetUsersResponseDataDTO(
    @SerializedName("users")
    val users: List<UserDTO>
)