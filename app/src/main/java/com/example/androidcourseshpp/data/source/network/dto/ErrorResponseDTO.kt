package com.example.androidcourseshpp.data.source.network.dto

import com.google.gson.annotations.SerializedName

data class ErrorResponseDTO(
    @SerializedName("message")
    val message: String
)