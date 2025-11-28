package com.example.androidcourseshpp.ui


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoEntity(
    val userName: String,
    val career: String,
    val address: String,
    val mobilePhone: String,
    val dateOfBirthday: String,
    val avatar: String
) : Parcelable
