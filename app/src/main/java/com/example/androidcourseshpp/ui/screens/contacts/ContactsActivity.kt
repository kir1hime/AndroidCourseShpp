package com.example.androidcourseshpp.ui.screens.contacts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity : BaseActivity() {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_contacts)
        adaptUserInterface(findViewById(R.id.fragmentContainer))
    }

}