package com.example.androidcourseshpp.ui.screens.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerUserInfoAdapter(
    fa: FragmentActivity,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fa) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}