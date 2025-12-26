package com.example.androidcourseshpp.data.models.userlist

import com.example.androidcourseshpp.ui.screens.main.contactdetails.ContactDetailsEntity

data class UserItem(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
    var isContact: Boolean
) {
    fun toContactDetailsEntity() =
        ContactDetailsEntity(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL
        )
}
