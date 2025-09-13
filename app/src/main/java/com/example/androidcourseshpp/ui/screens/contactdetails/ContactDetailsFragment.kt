package com.example.androidcourseshpp.ui.screens.contactdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentDetailviewBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL

class ContactDetailsFragment : BaseFragment() {

    private lateinit var binding : FragmentDetailviewBinding
    private val args : ContactDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailviewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilePhotoTransition()

        setContactDetailsInfo()
        setListeners()
    }

    private fun profilePhotoTransition(){
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_element_transition)
        binding.circleViewProfilePhoto.transitionName = args.contactDetails.id.toString()

    }

    private fun setContactDetailsInfo() = with(binding){
        circleViewProfilePhoto.loadImageFromURL(requireContext(), args.contactDetails.avatarURL)
        textViewName.text = args.contactDetails.name
        textViewCareer.text = args.contactDetails.career
    }

    private fun setListeners(){
        binding.imageButtonArrowBack.setOnClickListener{
            findNavController().navigateUp()
        }
    }
}