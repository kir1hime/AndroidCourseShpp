package com.example.androidcourseshpp.ui.screens.contract

import android.widget.ImageView
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

interface  Navigator {

    fun moveToMyProfileScreen()

    fun moveToDetailsScreen(contact : ContactItem, avatar: ImageView)

    fun moveBack()

}