package com.example.androidcourseshpp.data.network.model.contacts

import com.example.androidcourseshpp.data.network.model.UserModel
import com.google.gson.annotations.SerializedName

data class GetUserContactsResponseModel(
    @SerializedName("contacts")
    val contacts: List<UserModel>
)