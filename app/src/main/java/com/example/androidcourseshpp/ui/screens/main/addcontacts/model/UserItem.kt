package com.example.androidcourseshpp.ui.screens.main.addcontacts.model

import com.example.androidcourseshpp.ui.screens.main.contactdetails.model.ContactDetailsModel

data class UserItem(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
    var isContact: Boolean
) {
    fun toContactDetailsEntity() =
        ContactDetailsModel(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL
        )
}