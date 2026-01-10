package com.example.androidcourseshpp.domain.entity.auth

import android.graphics.Bitmap

data class SignUpInfo(
    val email: String,
    val password: String,
    val userName: String,
    val mobilePhone: String,
    val avatar: Bitmap
)
