package com.example.androidcourseshpp.domain.entity.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class UserInfo(
    val id: Int,
    val name: String,
    val mobilePhone: String,
    val address: String,
    val career: String,
    val dateOfBirthday: Date?,
    val avatar: String
) : Parcelable