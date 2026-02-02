package com.example.androidcourseshpp.ui.screens.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactDetailsModel(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
    val address: String
) : Parcelable