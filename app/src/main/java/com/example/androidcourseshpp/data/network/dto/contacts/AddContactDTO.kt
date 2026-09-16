package com.example.androidcourseshpp.data.network.dto.contacts
import com.google.gson.annotations.SerializedName

data class AddContactRequestDTO(
    @SerializedName("contactId")
    val contactId: Long
)