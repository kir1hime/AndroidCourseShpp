package com.example.androidcourseshpp.ui.screens.main.addcontacts.model

import com.example.androidcourseshpp.domain.entity.user.UserListItemInfo
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel

data class UserItem(
    val id: Long,
    val name: String,
    val career: String,
    val avatarURL: String,
    val address: String,
    var isContact: Boolean
) {
    fun toContactDetails() =
        ContactDetailsModel(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL,
            address = address
        )
}

fun UserListItemInfo.toUserItem() =
    UserItem(
        id = id,
        name = name,
        career = career,
        avatarURL = avatarURL,
        isContact = isContact,
        address = address
    )