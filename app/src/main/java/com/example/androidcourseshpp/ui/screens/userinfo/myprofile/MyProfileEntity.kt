package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyProfileEntity(
    val userName: String,
    val career: String,
    val address: String
) : Parcelable