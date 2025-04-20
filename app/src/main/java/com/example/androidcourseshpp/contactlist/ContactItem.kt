package com.example.androidcourseshpp.contactlist

class ContactItem(private val name : String, private val career : String, private val avatarResId : String ) {
    fun getName() = name
    fun getCareer() = career
    fun getAvatarResId() = avatarResId
}