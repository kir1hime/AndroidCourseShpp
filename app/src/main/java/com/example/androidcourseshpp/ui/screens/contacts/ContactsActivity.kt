package com.example.androidcourseshpp.ui.screens.contacts

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.ActivityContactsBinding
import com.example.androidcourseshpp.ui.extensions.adaptUserInterface
import com.example.androidcourseshpp.ui.screens.contacts.contract.Navigator
import com.example.androidcourseshpp.ui.screens.main.MainActivity

class ContactsActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityContactsBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adaptUserInterface(binding.root)

    }


    override fun moveToMyProfileScreen() {
        val intent = Intent(this, MainActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_from_left_to_right,
            R.anim.slide_out_from_left_to_right
        )
        startActivity(intent, options.toBundle())
        finish()
    }

    override fun moveToDetailsScreen() {
        findNavController(R.id.fragmentContainer).navigate(
            R.id.action_contactListFragment_to_contactDetailsFragment, null,
            navOptions {
                anim {
                    enter = R.anim.slide_in_from_right_to_left
                    exit = R.anim.slide_out_from_right_to_left
                    popEnter = R.anim.slide_in_from_left_to_right
                    popExit = R.anim.slide_out_from_left_to_right
                }
            })
    }


    override fun moveBack() {
        findNavController(R.id.fragmentContainer).navigateUp()
    }

}