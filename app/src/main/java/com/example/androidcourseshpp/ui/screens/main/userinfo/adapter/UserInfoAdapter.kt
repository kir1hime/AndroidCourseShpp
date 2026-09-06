package com.example.androidcourseshpp.ui.screens.main.userinfo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidcourseshpp.ui.screens.main.userinfo.Tab
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.ContactListFragment
import com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile.UserProfileFragment

class UserInfoAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = Tab.entries.size

    override fun createFragment(position: Int): Fragment =
        when (Tab.entries[position]) {
            Tab.USER_PROFILE -> UserProfileFragment()
            Tab.CONTACTS -> ContactListFragment()
        }

}