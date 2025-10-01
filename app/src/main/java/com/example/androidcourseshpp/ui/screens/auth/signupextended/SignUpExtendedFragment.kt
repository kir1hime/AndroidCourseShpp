package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.databinding.FragmentSignUpExtendedBinding
import com.example.androidcourseshpp.ui.screens.auth.AuthFragment

class SignUpExtendedFragment : AuthFragment() {

    private lateinit var binding: FragmentSignUpExtendedBinding
    private val args by navArgs<SignUpExtendedFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpExtendedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() = with(binding){
        buttonForward.setOnClickListener {
            moveToMyProfileScreen(args.userEmail)
        }
        buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }

    }






}