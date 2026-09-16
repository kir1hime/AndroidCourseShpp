package com.example.androidcourseshpp.data.network.dto.contacts

import com.example.androidcourseshpp.data.network.dto.common.UserDTO
import com.google.gson.annotations.SerializedName

data class GetUserContactsResponseDTO(
    @SerializedName("data")
    val data: GetUserContactsResponseDataDTO
)

data class GetUserContactsResponseDataDTO(
    @SerializedName("contacts")
    val contacts: List<UserDTO>
)