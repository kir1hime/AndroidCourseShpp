package com.example.androidcourseshpp.ui.screens.auth.signup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpModel(
    val email: String,
    val password: String,
    val toRememberUser: Boolean
) : Parcelable