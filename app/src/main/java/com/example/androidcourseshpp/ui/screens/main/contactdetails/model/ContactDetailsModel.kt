package com.example.androidcourseshpp.ui.screens.main.contactdetails.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactDetailsModel(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String
) : Parcelable