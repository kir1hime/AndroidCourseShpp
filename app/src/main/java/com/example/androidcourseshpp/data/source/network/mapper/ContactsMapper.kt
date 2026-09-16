package com.example.androidcourseshpp.data.source.network.mapper

import com.example.androidcourseshpp.data.source.network.dto.contacts.GetUserContactsResponseDTO
import com.example.androidcourseshpp.data.source.network.model.contacts.GetUserContactsResponseModel

fun GetUserContactsResponseDTO.toGetUserContactsResponseModel() = with(data) {
    GetUserContactsResponseModel(
        contacts = contacts.map { it.toUserModel() }
    )
}