package com.example.androidcourseshpp.data.source.network.model.contacts

import com.example.androidcourseshpp.data.source.network.model.common.UserModel

data class GetUserContactsResponseModel(
    val contacts: List<UserModel>
)