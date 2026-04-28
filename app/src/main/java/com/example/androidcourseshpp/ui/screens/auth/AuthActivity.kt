package com.example.androidcourseshpp.ui.screens.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.androidcourseshpp.databinding.ActivityAuthBinding
import com.example.androidcourseshpp.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptUserInterface(binding.root)

    }

}
