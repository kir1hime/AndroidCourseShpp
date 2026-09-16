package com.example.androidcourseshpp.data.source.network.dto.user

import com.example.androidcourseshpp.data.source.network.model.user.GetUsersResponseModel
import com.google.gson.annotations.SerializedName


data class GetUsersResponseDTO(
    @SerializedName("data")
    val data: GetUsersResponseModel
)