package com.example.androidcourseshpp.ui

import androidx.fragment.app.Fragment
import com.example.androidcourseshpp.ui.screens.contacts.contract.Navigator

open class NavigatedFragment : Fragment() {

    fun Fragment.navigator() : Navigator {
        return requireActivity() as Navigator
    }
}