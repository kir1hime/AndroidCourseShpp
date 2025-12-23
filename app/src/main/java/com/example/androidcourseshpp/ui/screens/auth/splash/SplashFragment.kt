package com.example.androidcourseshpp.ui.screens.auth.splash

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
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
    }

    override fun setObservers() = with(binding) {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is SplashContract.Effect.NavigateToSignInScreen -> moveToSignInScreen()
                is SplashContract.Effect.NavigateToMyProfileScreen -> moveToMyProfileScreen(effect.userServerId)
            }
        }
    }

    private fun moveToSignInScreen() {
        val direction = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
        findNavController().navigate(direction)
    }
}