package com.example.androidcourseshpp.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentMainBinding
import com.example.androidcourseshpp.ui.screens.contacts.ContactListFragment
import com.google.android.material.tabs.TabLayoutMediator

private const val FIRST_FRAGMENT_POS_IN_TAB = 0
private const val SECOND_FRAGMENT_POS_IN_TAB = 1

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
            tabItem.text = when (position) {
                FIRST_FRAGMENT_POS_IN_TAB -> getString(R.string.my_profile_label)
                SECOND_FRAGMENT_POS_IN_TAB -> getString(R.string.contacts_label)
                else -> throw IllegalArgumentException()
            }
        }.attach()
    }
}