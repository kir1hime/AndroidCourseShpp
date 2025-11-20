package com.example.androidcourseshpp.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.databinding.FragmentSplashBinding
import com.example.androidcourseshpp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
    }

    private fun setObservers() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is SplashContract.Effect.NavigateToSignUpScreen -> moveToSignUpScreen()
                is SplashContract.Effect.NavigateToMyProfileScreen -> moveToMyProfileScreen(effect.userInfo)
            }
        }
    }

    private fun moveToSignUpScreen() {
        val direction = SplashFragmentDirections.actionSplashFragmentToSignUpFragment()
        findNavController().navigate(direction)
    }


}