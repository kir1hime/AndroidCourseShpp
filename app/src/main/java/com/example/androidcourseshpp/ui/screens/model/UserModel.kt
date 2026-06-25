package com.example.androidcourseshpp.ui.screens.model

import android.os.Parcelable
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class UserModel(
    val id: Long,
    val name: String,
    val mobilePhone: String,
    val address: String,
    val career: String,
    val dateOfBirthday: Date?,
    val avatar: String
) : Parcelable {
    fun toUserInfo() =
        UserInfo(
            id = id,
            name = name,
            career = career,
            mobilePhone = mobilePhone,
            address = address,
            dateOfBirthday = dateOfBirthday,
            avatar = avatar
        )
}

fun UserInfo.toUserModel() =
    UserModel(
        id = id,
        name = name,
        career = career,
        mobilePhone = mobilePhone,
        address = address,
        dateOfBirthday = dateOfBirthday,
        avatar = avatar
    )
