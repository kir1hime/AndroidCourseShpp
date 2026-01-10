package com.example.androidcourseshpp.domain.entity

import com.example.androidcourseshpp.domain.entity.ContactDetailsEntity

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