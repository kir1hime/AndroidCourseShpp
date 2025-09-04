package com.example.androidcourseshpp.ui.screens.contacts

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.fragment.app.commit
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.databinding.ActivityContactsBinding
import com.example.androidcourseshpp.ui.BaseActivity
import com.example.androidcourseshpp.ui.screens.contacts.contract.Navigator
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity : BaseActivity(), Navigator {

    private lateinit var binding: ActivityContactsBinding
    private lateinit var navController: NavController

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityContactsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adaptUserInterface(binding.root)

        receiveNavController()
    }

    private fun receiveNavController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
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

    override fun moveToDetailsScreen(contact: ContactItem, avatar: ImageView) {
        val extras = FragmentNavigatorExtras(avatar to contact.id.toString())

        val direction =
            ContactListFragmentDirections.actionContactListFragmentToContactDetailsFragment(contact)

        navController.navigate(direction, extras)
    }


    override fun moveBack() {
        navController.navigateUp()
    }

}