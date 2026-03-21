package com.example.androidcourseshpp.ui.screens.main.userinfo

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentUserinfoBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.main.userinfo.adapter.UserInfoAdapter
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.Searchable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserInfoFragment : BaseFragment<FragmentUserinfoBinding>(FragmentUserinfoBinding::inflate),
    TabSwitchable {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        setListeners()
    }

    override fun setListeners() {
        binding.tabLayoutUserInfo.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == Tab.USER_PROFILE.ordinal) {

                    childFragmentManager.fragments.find { fragment ->
                        if (fragment is Searchable) {
                            fragment.hideSearchBar()
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

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            when (Tab.entries[binding.viewPagerUserInformation.currentItem]) {
                Tab.USER_PROFILE -> requireActivity().finish()
                Tab.CONTACTS -> moveToUserProfileTab()
            }
        }
    }

    private fun initViewPager() = with(binding) {
        val adapter = UserInfoAdapter(this@UserInfoFragment)
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