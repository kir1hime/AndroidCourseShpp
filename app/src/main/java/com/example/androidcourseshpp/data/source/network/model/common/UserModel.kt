package com.example.androidcourseshpp.data.source.network.model.common

import java.util.Date

data class UserModel(
    val id: Long,
    val email: String,
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: Date?,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?,
    val image: String?
)