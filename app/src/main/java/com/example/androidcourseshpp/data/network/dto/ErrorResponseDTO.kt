package com.example.androidcourseshpp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ErrorResponseDTO(
    @SerializedName("message")
    val message: String
)