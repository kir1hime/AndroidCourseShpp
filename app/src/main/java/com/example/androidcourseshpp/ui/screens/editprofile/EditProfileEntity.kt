package com.example.androidcourseshpp.ui.screens.editprofile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class EditProfileEntity(
    val userName: String,
    val career: String,
    val address: String,
    val mobilePhone: String,
    val dateOfBirthday: String
) : Parcelable