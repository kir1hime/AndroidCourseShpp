package com.example.androidcourseshpp.data.network.dto.entity

import com.example.androidcourseshpp.ui.UserInfoEntity

data class User(
    val id: Long,
    val email: String,
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?,
    val image: String?
) {
    fun toUserInfoEntity() = UserInfoEntity(
        userName = name ?: "",
        career = career ?: "",
        address = address ?: "",
        mobilePhone = phone ?: "",
        dateOfBirthday = birthday ?: ""
    )
}