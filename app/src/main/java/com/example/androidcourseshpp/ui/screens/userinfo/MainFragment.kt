package com.example.androidcourseshpp.ui.screens.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.Tab
import com.example.androidcourseshpp.databinding.FragmentMainBinding
import com.example.androidcourseshpp.ui.screens.userinfo.contacts.ContactListFragment
import com.example.androidcourseshpp.ui.screens.userinfo.myprofile.MyProfileFragment
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val fragments = listOf(MyProfileFragment(), ContactListFragment())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() = with(binding) {
        val adapter = ViewPagerUserInfoAdapter(this@MainFragment, fragments)
        vpUserInfo.adapter = adapter

        TabLayoutMediator(tlMyProfileContacts, vpUserInfo) { tabItem, position ->

            tabItem.text = when (Tab.entries[position]) {
                Tab.MYPROFILE -> getString(R.string.my_profile_label)
                Tab.CONTACTS -> getString(R.string.contacts_label)
            }

        }.attach()
    }

     fun goToTub(tab : Tab){
        binding.vpUserInfo.currentItem = tab.ordinal
    }

}