package com.example.androidcourseshpp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

open class BaseActivity<VBinding : ViewBinding>(private val inflaterMethod: (LayoutInflater) -> VBinding) :  AppCompatActivity() {
    private var _binding: VBinding? = null
    val binding get() = requireNotNull(_binding)

    protected fun adaptUserInterface(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = inflaterMethod.invoke(layoutInflater)

        setContentView(binding.root)
        adaptUserInterface(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

