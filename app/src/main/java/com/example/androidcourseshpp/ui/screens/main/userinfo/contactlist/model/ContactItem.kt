package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.ui.screens.main.contactdetails.model.ContactDetailsModel


data class ContactItem(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
) {
    fun toContactDetailsEntity() =
        ContactDetailsModel(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL
        )

    fun toContactInfo() =
        ContactInfo(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL
        )
}

fun ContactInfo.toContactItem() =
    ContactItem(
        id = id,
        name = name,
        career = career,
        avatarURL = avatarURL
    )


data class SelectableContactItem(val item: ContactItem, var isSelectionModeEnabled: Boolean)