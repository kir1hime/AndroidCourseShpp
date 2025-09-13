package com.example.androidcourseshpp.ui.screens.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.Tab
import com.example.androidcourseshpp.databinding.FragmentUserinfoBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.userinfo.adapter.UserInfoAdapter
import com.example.androidcourseshpp.ui.screens.userinfo.contactlist.ContactListFragment
import com.example.androidcourseshpp.ui.screens.userinfo.myprofile.MyProfileFragment
import com.google.android.material.tabs.TabLayoutMediator

class UserInfoFragment : BaseFragment(), TabSwitchable {

    private lateinit var binding: FragmentUserinfoBinding
    private val tabFragments = listOf(MyProfileFragment(), ContactListFragment())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserinfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() = with(binding) {
        val adapter = UserInfoAdapter(this@UserInfoFragment, tabFragments)
        viewPagerUserInformation.adapter = adapter
        TabLayoutMediator(tabLayoutUserInfo, viewPagerUserInformation) { tabItem, position ->

            tabItem.text = when(Tab.entries[position]){

                Tab.MYPROFILE -> getString(R.string.my_profile)
                Tab.CONTACTS ->getString(R.string.contacts)
            }

        }.attach()
    }

    override fun moveToMyProfileTab() {
        binding.viewPagerUserInformation.currentItem = Tab.MYPROFILE.ordinal
    }

    override fun moveToContactsTab() {
        binding.viewPagerUserInformation.currentItem = Tab.CONTACTS.ordinal
    }
}