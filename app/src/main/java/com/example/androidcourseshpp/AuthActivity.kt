package com.example.androidcourseshpp

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Patterns
import android.widget.AutoCompleteTextView.Validator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidcourseshpp.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.auth)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btRegister.setOnClickListener {
            checkEMailInput()
        }

    }

    private fun checkEMailInput(): Boolean {
        val userEMail: String = binding.etEMail.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(userEMail).matches()) {
            binding.tilEMail.helperText = getString(R.string.email_error)
            return false
        } else {
            binding.tilEMail.helperText = null
        }
        return true
    }
}