package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpExtendedEntity(
    val userServerId: Long,
    val toRememberUser: Boolean
) : Parcelable