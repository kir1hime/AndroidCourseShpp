package com.example.androidcourseshpp.contactlist

class ContactItem(private val name : String, private val career : String, private val avatarURL : String ) {
    fun getName() = name
    fun getCareer() = career
    fun getAvatarURL() = avatarURL
}