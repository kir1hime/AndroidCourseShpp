package com.example.androidcourseshpp.ui

import androidx.fragment.app.Fragment
import com.example.androidcourseshpp.ui.screens.contract.Navigator

open class NavigatedFragment : BaseFragment() {

    fun Fragment.navigator() : Navigator {
        return requireActivity() as Navigator
    }

}