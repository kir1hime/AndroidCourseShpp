package com.example.androidcourseshpp.data.network.model.contacts

import com.example.androidcourseshpp.data.network.model.common.UserModel

data class GetUserContactsResponseModel(
    val contacts: List<UserModel>
)