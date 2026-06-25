package com.example.androidcourseshpp.data.source.network.model.user

import com.example.androidcourseshpp.data.source.network.model.UserModel
import com.google.gson.annotations.SerializedName

data class GetUserResponseModel(
    @SerializedName("user")
    val user: UserModel
)