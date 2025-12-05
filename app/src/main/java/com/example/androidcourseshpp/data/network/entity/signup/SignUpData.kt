package com.example.androidcourseshpp.data.network.entity.signup

import okhttp3.MultipartBody

data class SignUpData(
    val email: String,
    val password: String,
    val userName: String,
    val mobilePhone: String,
    val image: MultipartBody.Part
)