package com.example.androidcourseshpp.ui.screens.contacts

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.commit
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.ActivityContactsBinding
import com.example.androidcourseshpp.ui.BaseActivity
import com.example.androidcourseshpp.ui.screens.contacts.contract.Navigator
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity : BaseActivity(), Navigator {

    private lateinit var binding: ActivityContactsBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adaptUserInterface(binding.root)

        if (savedInstanceState == null) {
            attachStartingFragment()
        }
    }

    private fun attachStartingFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, ContactListFragment())
            .commit()
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
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in_from_right_to_left,
                R.anim.slide_out_from_right_to_left,
                R.anim.slide_in_from_left_to_right,
                R.anim.slide_out_from_left_to_right
            )
            addToBackStack(null)
            replace(R.id.fragmentContainer, ContactDetailsFragment())
        }
    }


    override fun moveBack() {
        supportFragmentManager.popBackStack()
    }

}