package com.example.androidcourseshpp.ui.screens.auth.signup.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpUserInfo(
    val email: String,
    val password: String,
    val toRememberUser: Boolean
) : Parcelable