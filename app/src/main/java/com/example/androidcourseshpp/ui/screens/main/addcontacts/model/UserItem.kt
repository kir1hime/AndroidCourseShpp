package com.example.androidcourseshpp.ui.screens.main.addcontacts.model

import com.example.androidcourseshpp.domain.entity.user.UserListItemInfo
import com.example.androidcourseshpp.ui.screens.main.contactdetails.model.ContactDetailsModel

data class UserItem(
    val id: Long,
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

fun UserListItemInfo.toUserItem() =
    UserItem(
        id = id,
        name = name,
        career = career,
        avatarURL = avatarURL,
        isContact = isContact
    )