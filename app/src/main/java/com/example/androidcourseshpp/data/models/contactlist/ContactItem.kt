package com.example.androidcourseshpp.data.models.contactlist

import com.example.androidcourseshpp.ui.screens.main.contactdetails.ContactDetailsEntity

data class ContactItem(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
) {
    fun toContactDetailsEntity() =
        ContactDetailsEntity(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL
        )
}


data class SelectableContactItem(val item: ContactItem, var isSelectionModeEnabled: Boolean)