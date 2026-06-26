package com.example.androidcourseshpp.ui.screens.main.userinfo

import android.os.Bundle
import android.view.View
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentUserinfoBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.main.userinfo.adapter.UserInfoAdapter
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.ContactListFragment
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.Searchable
import com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile.UserProfileFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserInfoFragment : BaseFragment<FragmentUserinfoBinding>(FragmentUserinfoBinding::inflate),
    TabSwitchable {
    private val tabFragments = listOf(UserProfileFragment(), ContactListFragment())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        setListeners()
    }

    private fun setListeners() = with(binding) {
        tabLayoutUserInfo.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == Tab.USER_PROFILE.ordinal) {

                    childFragmentManager.fragments.find { fragment ->
                        if (fragment is Searchable) {
                            fragment.hideSearchBar()
                            true
                        }
                        false
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun initViewPager() = with(binding) {
        val adapter = UserInfoAdapter(this@UserInfoFragment, tabFragments)
        viewPagerUserInformation.adapter = adapter
        TabLayoutMediator(tabLayoutUserInfo, viewPagerUserInformation) { tabItem, position ->

            tabItem.text = when (Tab.entries[position]) {
                Tab.USER_PROFILE -> getString(R.string.user_profile)
                Tab.CONTACTS -> getString(R.string.contacts)
            }

        }.attach()
    }

    override fun moveToUserProfileTab() {
        binding.viewPagerUserInformation.currentItem = Tab.USER_PROFILE.ordinal
    }

    override fun moveToContactsTab() {
        binding.viewPagerUserInformation.currentItem = Tab.CONTACTS.ordinal
    }
}