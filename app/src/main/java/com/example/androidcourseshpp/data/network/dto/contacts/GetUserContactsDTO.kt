package com.example.androidcourseshpp.data.network.dto.contacts

import com.example.androidcourseshpp.data.network.model.contacts.GetUserContactsResponseModel
import com.google.gson.annotations.SerializedName

data class GetUserContactsResponseDTO(
    @SerializedName("data")
    val data: GetUserContactsResponseModel
)