package com.example.androidcourseshpp.ui.screens.main.contactdetails.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactDetailsEntity(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String
) : Parcelable