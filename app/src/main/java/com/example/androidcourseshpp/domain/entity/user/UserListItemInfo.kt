package com.example.androidcourseshpp.domain.entity.user

data class UserListItemInfo(
    val id: Long,
    val name: String,
    val career: String,
    val avatarURL: String,
    var isContact: Boolean
)