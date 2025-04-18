package com.example.androidcourseshpp

class ContactItem(private val name : String, private val career : String, private val avatarResId : Int ) {
    fun getName() = name
    fun getCareer() = career
    fun getAvatarResId() = avatarResId
}