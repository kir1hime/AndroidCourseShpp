package com.example.androidcourseshpp.ui.screens

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.databinding.ActivityMainBinding
import com.example.androidcourseshpp.ui.extensions.adaptUserInterface
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity
import com.example.androidcourseshpp.ui.screens.userinfo.MainFragmentDirections


class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private  val navController: NavController by lazy {
        navHostFragment.navController
    }
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainer1) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptUserInterface(binding.root)

    }


    override fun moveToAuthScreen() {
        val intent = Intent(this, AuthActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_from_left_to_right,
            R.anim.slide_out_from_left_to_right
        )

        startActivity(intent, options.toBundle())
        finish()
    }

    override fun moveToDetailsScreen(contact: ContactItem, avatar: ImageView) {
        val extras = FragmentNavigatorExtras(avatar to contact.id.toString())

        val direction = MainFragmentDirections
            .actionMainFragmentToContactDetailsFragment(
                contact.id,
                contact.name,
                contact.career,
                contact.avatarURL
            )

        navController.navigate(direction, extras)
    }

    override fun moveBack() {
        navController.navigateUp()
    }


}

