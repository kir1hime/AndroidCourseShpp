package com.example.androidcourseshpp.data.source.network.model

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.entity.user.UserListItemInfo
import java.util.Date

data class UserModel(
    val id: Long,
    val email: String,
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: Date?,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?,
    val image: String?
) {
    fun toUserInfo() =
        UserInfo(
            id = id,
            name = name ?: "",
            mobilePhone = phone ?: "",
            address = address ?: "",
            career = career ?: "",
            avatar = image ?: "",
            dateOfBirthday = birthday
        )

    fun toUserItemInfo(isContact: Boolean) =
        UserListItemInfo(
            id = id,
            name = name ?: "",
            career = career ?: "",
            avatarURL = image ?: "",
            isContact = isContact
        )

    fun toContactInfo() =
        ContactInfo(
            id = id,
            name = name ?: "",
            career = career ?: "",
            avatarURL = image ?: ""
        )
}