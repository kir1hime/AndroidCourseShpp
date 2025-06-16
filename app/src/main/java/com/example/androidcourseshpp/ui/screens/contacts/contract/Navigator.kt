package com.example.androidcourseshpp.ui.screens.contacts.contract

import androidx.fragment.app.Fragment
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

fun Fragment.navigator() : Navigator {
    return requireActivity() as Navigator
}

interface  Navigator {

    fun moveToMyProfileScreen()

    fun moveToDetailsScreen(contact : ContactItem)

    fun moveBack()

}