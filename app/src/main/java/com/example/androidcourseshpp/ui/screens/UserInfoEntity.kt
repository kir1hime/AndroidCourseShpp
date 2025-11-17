package com.example.androidcourseshpp.ui.screens

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoEntity(
    val userName: String,
    val address: String,
    val dateOfBirthday: String,
    val career: String,
    val mobilePhone: String
) : Parcelable