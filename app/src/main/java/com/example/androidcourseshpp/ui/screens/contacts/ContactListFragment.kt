package com.example.androidcourseshpp.ui.screens.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidcourseshpp.databinding.FragmentContactlistBinding

class ContactListFragment : Fragment() {

    private lateinit var binding: FragmentContactlistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentContactlistBinding.inflate(inflater, container, false)

        return binding.root
    }
}