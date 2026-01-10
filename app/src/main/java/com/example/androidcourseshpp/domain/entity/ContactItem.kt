package com.example.androidcourseshpp.domain.entity

import com.example.androidcourseshpp.domain.entity.ContactDetailsEntity

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