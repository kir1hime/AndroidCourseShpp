package com.example.androidcourseshpp.domain.entity.user

import java.util.Date

data class UserInfo(
    val id: Long,
    val name: String,
    val mobilePhone: String,
    val address: String,
    val career: String,
    val dateOfBirthday: Date?,
    val avatar: String
)

fun UserInfo.toUserItemInfo(isContact: Boolean) =
    UserItemInfo(
        id = id,
        name = name,
        career = career,
        avatarURL = avatar,
        address = address,
        isContact = isContact
    )