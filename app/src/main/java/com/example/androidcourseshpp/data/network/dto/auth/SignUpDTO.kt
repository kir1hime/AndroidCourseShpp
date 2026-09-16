package com.example.androidcourseshpp.data.network.dto.auth

import com.example.androidcourseshpp.data.network.model.auth.SignUpResponseModel
import com.google.gson.annotations.SerializedName

data class SignUpResponseDTO(
    @SerializedName("data")
    val data: SignUpResponseModel
)



