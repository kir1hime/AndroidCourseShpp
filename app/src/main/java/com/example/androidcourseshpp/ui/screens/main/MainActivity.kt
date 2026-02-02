package com.example.androidcourseshpp.ui.screens.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.ActivityMainBinding
import com.example.androidcourseshpp.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>
    (ActivityMainBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPushNotificationPermission()
    }

    private fun registerPushNotificationPermission() {
        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val isGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!isGranted) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent) {
        val navController = findNavController(R.id.fragmentContainer)
        val uri = intent.data

        uri?.let {
            val request = NavDeepLinkRequest.Builder
                .fromUri(uri)
                .build()

            val navOptions = NavOptions.Builder()
                .setExitAnim(R.anim.slide_out_from_top_to_bottom)
                .setEnterAnim(R.anim.slide_in_from_top_to_bottom)
                .setPopEnterAnim(R.anim.slide_in_from_left_to_right)
                .setPopExitAnim(R.anim.slide_out_from_left_to_right)
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .build()

            navController.navigate(request, navOptions)
        }
    }
}