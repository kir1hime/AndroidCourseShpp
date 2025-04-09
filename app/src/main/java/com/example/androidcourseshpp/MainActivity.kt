package com.example.androidcourseshpp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidcourseshpp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val EMAIL_KEY = "EMAIL_KEY"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userEMail = intent.getStringExtra(EMAIL_KEY)
        binding.tvName.text = parseEMail(userEMail.toString())
    }

    private fun parseEMail(eMail: String): String {
        val parsedName = StringBuilder()

        for (ch in eMail) {
            if (ch == '@') {
                break
            }
            if (ch == '.') {
                parsedName.append(" ")
                continue
            }
            if (ch.isLetter()) {
                parsedName.append(ch)
            }

        }

        parsedName[0] = parsedName[0].uppercaseChar()
        parsedName[parsedName.indexOf(' ') + 1] =
            parsedName[parsedName.indexOf(' ') + 1].uppercaseChar()

        return parsedName.toString()
    }

}