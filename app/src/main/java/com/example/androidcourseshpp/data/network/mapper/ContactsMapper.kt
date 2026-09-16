package com.example.androidcourseshpp.data.network.mapper

import com.example.androidcourseshpp.data.network.dto.contacts.GetUserContactsResponseDTO
import com.example.androidcourseshpp.data.network.model.contacts.GetUserContactsResponseModel

fun GetUserContactsResponseDTO.toGetUserContactsResponseModel() = with(data) {
    GetUserContactsResponseModel(
        contacts = contacts.map { it.toUserModel() }
    )
}