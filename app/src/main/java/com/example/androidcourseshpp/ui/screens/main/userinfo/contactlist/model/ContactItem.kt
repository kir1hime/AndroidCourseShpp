package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel


data class ContactItem(
    val id: Long,
    val name: String,
    val career: String,
    val address: String,
    val avatarURL: String,
) {
    fun toContactDetails() =
        ContactDetailsModel(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL,
            address = address
        )

    fun toContactInfo() =
        ContactInfo(
            id = id,
            name = name,
            career = career,
            address = address,
            avatarURL = avatarURL
        )
}

fun ContactInfo.toContactItem() =
    ContactItem(
        id = id,
        name = name,
        career = career,
        address = address,
        avatarURL = avatarURL
    )


data class SelectableContactItem(val item: ContactItem, var isSelectMode: Boolean)