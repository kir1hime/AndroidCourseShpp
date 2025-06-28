package com.example.androidcourseshpp.ui.screens

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

fun Fragment.navigator() : Navigator{
    return requireActivity() as Navigator
}

interface Navigator {

    fun moveToAuthScreen()

    fun moveToDetailsScreen(contact : ContactItem, avatar: ImageView)

    fun moveBack()

}