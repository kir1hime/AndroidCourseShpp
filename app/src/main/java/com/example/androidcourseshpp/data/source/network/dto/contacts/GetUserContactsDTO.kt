package com.example.androidcourseshpp.data.source.network.dto.contacts

import com.example.androidcourseshpp.data.source.network.model.contacts.GetUserContactsModel
import com.google.gson.annotations.SerializedName

data class GetUserContactsResponseDTO(
    @SerializedName("data")
    val data: GetUserContactsModel
)