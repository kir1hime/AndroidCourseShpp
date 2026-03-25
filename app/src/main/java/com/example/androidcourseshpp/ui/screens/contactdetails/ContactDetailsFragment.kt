package com.example.androidcourseshpp.ui.screens.contactdetails

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentDetailviewBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.extensions.loadImageFromURLCircled

class ContactDetailsFragment :
    BaseFragment<FragmentDetailviewBinding>(FragmentDetailviewBinding::inflate) {

    private val args: ContactDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilePhotoTransition()

        setContactDetailsInfo()
        setListeners()
    }

    private fun profilePhotoTransition() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_element_transition)
        binding.circleViewProfilePhoto.transitionName = args.contactDetails.id.toString()

    }

    private fun setContactDetailsInfo() = with(binding){
        circleViewProfilePhoto.loadImageFromURLCircled(requireContext(), args.contactDetails.avatarURL)
        textViewName.text = args.contactDetails.name
        textViewCareer.text = args.contactDetails.career
    }

    private fun setListeners() {
        binding.imageButtonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}