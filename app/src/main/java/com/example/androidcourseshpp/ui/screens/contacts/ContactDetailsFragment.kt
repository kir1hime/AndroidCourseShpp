package com.example.androidcourseshpp.ui.screens.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.data.ImageLoader
import com.example.androidcourseshpp.databinding.FragmentDetailviewBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL
import com.example.androidcourseshpp.ui.screens.contacts.contract.navigator

class ContactDetailsFragment : Fragment() {

    private lateinit var binding : FragmentDetailviewBinding
    private val args : ContactDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailviewBinding.inflate(inflater, container, false)

        setContactDetailsInfo()
        setListeners()

        return binding.root
    }

    private fun setContactDetailsInfo() = with(binding){
        IvProfPhoto.loadImageFromURL(requireContext(), args.avatar, ImageLoader.GLIDE)
        tvName.text = args.name
        tvCareer.text = args.career
    }

    private fun setListeners(){
        binding.ibtArrowBack.setOnClickListener{
            navigator().moveBack()
        }
    }
}