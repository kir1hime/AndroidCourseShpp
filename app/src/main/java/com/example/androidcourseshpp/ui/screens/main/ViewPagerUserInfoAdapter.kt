package com.example.androidcourseshpp.ui.screens.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerUserInfoAdapter(
    fragment: Fragment,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}