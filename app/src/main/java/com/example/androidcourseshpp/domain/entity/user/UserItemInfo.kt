package com.example.androidcourseshpp.domain.entity.user

data class UserItemInfo(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
    val address: String,
    var isContact: Boolean
)