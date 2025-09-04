package com.example.androidcourseshpp.ui.screens.contacts.contract

interface  Navigator {

    fun moveToMyProfileScreen()

    fun moveToDetailsScreen(contact : ContactItem, avatar:ImageView)

    fun moveBack()

}