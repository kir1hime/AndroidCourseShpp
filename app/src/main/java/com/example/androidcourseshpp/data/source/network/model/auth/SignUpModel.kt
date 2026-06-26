package com.example.androidcourseshpp.data.source.network.model.auth

import com.example.androidcourseshpp.data.source.network.model.UserModel
import okhttp3.MultipartBody

data class SignUpRequestModel(
    val email: String,
    val password: String,
    val userName: String,
    val mobilePhone: String,
    val image: MultipartBody.Part
)
data class SignUpResponseModel(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String
)
