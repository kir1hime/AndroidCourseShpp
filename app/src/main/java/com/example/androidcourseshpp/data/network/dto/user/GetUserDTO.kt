package com.example.androidcourseshpp.data.network.dto.user

import com.example.androidcourseshpp.data.network.model.user.GetUserResponseModel
import com.google.gson.annotations.SerializedName

data class GetUserResponseDTO(
    @SerializedName("data")
    val data: GetUserResponseModel
)
