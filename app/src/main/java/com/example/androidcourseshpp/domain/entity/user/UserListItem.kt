package com.example.androidcourseshpp.domain.entity.user

data class UserListItem(
    val id: Long,
    val name: String,
    val career: String,
    val avatarURL: String,
    val address: String,
    var isContact: Boolean
)