package com.example.androidcourseshpp.data.source.network.model.auth

import com.example.androidcourseshpp.data.source.network.model.UserModel
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class SignUpRequestModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("mobilePhone")
    val mobilePhone: String,
    @SerializedName("image")
    val image: MultipartBody.Part
)

data class SignUpResponseModel(
    @SerializedName("user")
    val user: UserModel,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)