package com.example.androidcourseshpp.data.network.service.auth.entity

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

data class SignUpData(
    val email: String,
    val password: String,
    val userName: String,
    val mobilePhone: String,
    val image: MultipartBody.Part
)