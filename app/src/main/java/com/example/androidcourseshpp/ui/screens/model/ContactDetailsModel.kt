package com.example.androidcourseshpp.ui.screens.model

import android.os.Parcelable
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactDetailsModel(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
    val address: String
) : Parcelable

fun UserInfo.toContactDetails() =
    ContactDetailsModel(
        id = id,
        name = name,
        career = career,
        avatarURL = avatar,
        address = address
    )