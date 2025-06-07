package com.example.androidcourseshpp.ui.screens.contacts.contract

import androidx.fragment.app.Fragment

fun Fragment.navigator() : Navigator {
    return requireActivity() as Navigator
}

interface  Navigator {

    fun moveToMyProfileScreen()

}