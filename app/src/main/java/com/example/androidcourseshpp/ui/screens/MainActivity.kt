package com.example.androidcourseshpp.ui.screens

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.ActivityMainBinding
import com.example.androidcourseshpp.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adaptUserInterface(binding.root)

    }
}

