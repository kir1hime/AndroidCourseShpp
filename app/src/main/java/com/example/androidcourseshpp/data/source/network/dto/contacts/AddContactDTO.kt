package com.example.androidcourseshpp.data.source.network.dto.contacts
import com.google.gson.annotations.SerializedName

data class AddContactRequestDTO(
    @SerializedName("contactId")
    val contactId: Long
)