package com.example.androidcourseshpp.data.source.network.dto.auth

import com.example.androidcourseshpp.data.source.network.model.auth.SignUpResponseModel
import com.google.gson.annotations.SerializedName

data class SignUpResponseDTO(
    @SerializedName("data")
    val data: SignUpResponseModel
)



