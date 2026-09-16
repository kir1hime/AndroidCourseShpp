package com.example.androidcourseshpp.data.network.dto.user

import com.example.androidcourseshpp.data.network.model.user.GetUsersResponseModel
import com.google.gson.annotations.SerializedName


data class GetUsersResponseDTO(
    @SerializedName("data")
    val data: GetUsersResponseModel
)