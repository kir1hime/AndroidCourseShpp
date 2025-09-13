package com.example.androidcourseshpp.ui.screens.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidcourseshpp.databinding.FragmentUserinfoBinding
import com.example.androidcourseshpp.ui.BaseFragment

class UserInfoFragment : BaseFragment() {

    private lateinit var binding: FragmentUserinfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserinfoBinding.inflate(inflater, container, false)

        return binding.root
    }
}