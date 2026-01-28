package com.example.androidcourseshpp.ui.screens.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
}