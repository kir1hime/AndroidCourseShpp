package com.example.androidcourseshpp.data.source.network.model.auth

import okhttp3.MultipartBody

data class SignUpData(
    val email: String,
    val password: String,
    val userName: String,
    val mobilePhone: String,
    val image: MultipartBody.Part
)