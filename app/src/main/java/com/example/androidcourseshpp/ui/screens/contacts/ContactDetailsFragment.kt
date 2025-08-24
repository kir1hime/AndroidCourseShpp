package com.example.androidcourseshpp.ui.screens.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.androidcourseshpp.R
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
        binding.IvProfPhoto.transitionName = args.contactDetails.id.toString()

    }

    private fun setContactDetailsInfo() = with(binding){
        IvProfPhoto.loadImageFromURL(requireContext(), args.contactDetails.avatarURL, ImageLoader.GLIDE, R.drawable.ic_defaultavatar2)
        tvName.text = args.contactDetails.name
        tvCareer.text = args.contactDetails.career
    }

    private fun setListeners(){
        binding.ibtArrowBack.setOnClickListener{
            navigator().moveBack()
        }
    }
}