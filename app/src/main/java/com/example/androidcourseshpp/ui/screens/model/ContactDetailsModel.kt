package com.example.androidcourseshpp.ui.screens.model

import android.os.Parcelable
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactDetailsModel(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
    val address: String,
) : Parcelable {
    fun toContactInfo() =
        ContactInfo(
            id = id,
            name = name,
            career = career,
            address = address,
            avatarURL = avatarURL
        )
}