package com.example.androidcourseshpp.data.source.network.model.user

import com.example.androidcourseshpp.data.source.network.model.UserModel
import com.google.gson.annotations.SerializedName

class GetUsersResponseModel(
    @SerializedName("users")
    val users: List<UserModel>
) {
}