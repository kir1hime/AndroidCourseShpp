package com.example.androidcourseshpp.ui.screens.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidcourseshpp.databinding.FragmentDetailviewBinding
import com.example.androidcourseshpp.ui.screens.contacts.contract.navigator

class DetailViewFragment : Fragment() {

    private lateinit var binding : FragmentDetailviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailviewBinding.inflate(inflater, container, false)

        setListeners()
        return binding.root
    }

    private fun setListeners(){
        binding.ibtArrowBack.setOnClickListener{
            navigator().moveBack()
        }
    }
}