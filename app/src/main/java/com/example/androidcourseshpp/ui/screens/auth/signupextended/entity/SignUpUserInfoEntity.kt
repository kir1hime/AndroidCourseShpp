package com.example.androidcourseshpp.ui.screens.auth.signupextended.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpUserInfoEntity(
    val email: String,
    val password: String,
    val toRememberUser: Boolean
) : Parcelable