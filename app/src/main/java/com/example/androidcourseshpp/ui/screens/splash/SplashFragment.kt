package com.example.androidcourseshpp.ui.screens.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.databinding.FragmentSplashBinding
import com.example.androidcourseshpp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
    }

    private fun setObservers() {
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