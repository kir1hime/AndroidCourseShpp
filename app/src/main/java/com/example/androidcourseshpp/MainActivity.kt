package com.example.androidcourseshpp

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidcourseshpp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userEMail = intent.getStringExtra(R.string.email_key.toString())
        binding.tvName.text = parseEMail(userEMail.toString())

        binding.btLogOut.setOnClickListener{
            val intent = Intent(this@MainActivity, AuthActivity::class.java)

            val options = ActivityOptions.makeCustomAnimation(
                this@MainActivity, R.anim.sing_up_fade_in, R.anim.my_profile_fade_out
            )

            startActivity(intent, options.toBundle())

            val editor = sharedPref.edit()
            editor.putString(R.string.email_key.toString(), "")
            editor.putString(R.string.pswd_key.toString(), "")
            editor.apply()

        }
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