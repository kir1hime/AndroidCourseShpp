package com.example.androidcourseshpp.data.source.network.model

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("career")
    val career: String?,
    @SerializedName("birthday")
    val birthday: Date?,
    @SerializedName("facebook")
    val facebook: String?,
    @SerializedName("instagram")
    val instagram: String?,
    @SerializedName("twitter")
    val twitter: String?,
    @SerializedName("linkedin")
    val linkedin: String?,
    @SerializedName("image")
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

    fun toContactInfo() =
        ContactInfo(
            id = id,
            name = name ?: "",
            career = career ?: "",
            address = address ?: "",
            avatarURL = image ?: ""
        )
}