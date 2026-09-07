package com.example.androidcourseshpp.data.source.network.model.user

import com.example.androidcourseshpp.data.source.network.model.common.UserModel
import java.util.Date

data class UpdateUserRequestModel(
    val id: Long,
    val name: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val career: String? = null,
    val birthday: Date? = null,
    val facebook: String? = null,
    val instagram: String? = null,
    val twitter: String? = null,
    val linkedin: String? = null,
    val image: String? = null
)

data class UpdateUserResponseModel(
    val user: UserModel
)