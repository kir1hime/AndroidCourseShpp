package com.example.androidcourseshpp.ui.screens.contactdetails

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
import com.example.androidcourseshpp.ui.utils.navigator

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
        binding.circleImageViewProfPhoto.transitionName = args.id.toString()

    }

    private fun setContactDetailsInfo() = with(binding){
        circleImageViewProfPhoto.loadImageFromURL(requireContext(), args.avatar, ImageLoader.GLIDE, R.drawable.ic_defaultavatar2)
        textViewName.text = args.name
        textViewCareer.text = args.career
    }

    private fun setListeners(){
        binding.imageButtonArrowBack.setOnClickListener{
           navigator().moveBack()
        }
    }
}