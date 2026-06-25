package com.example.androidcourseshpp.ui.screens.auth.splash

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
        collectFlowWithLifecycle(viewModel.effect) { effect ->
            when (effect) {
                is SplashContract.Effect.NavigateToSignInScreen -> moveToSignInScreen()
                is SplashContract.Effect.NavigateToUserProfileScreen -> moveToUserProfileScreen(effect.userInfo)
            }
        }
    }

    private fun moveToSignInScreen() {
        val direction = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
        findNavController().navigate(direction)
    }
}