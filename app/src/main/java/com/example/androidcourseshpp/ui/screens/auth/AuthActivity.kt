package com.example.androidcourseshpp.ui.screens.auth

import com.example.androidcourseshpp.databinding.ActivityAuthBinding
import com.example.androidcourseshpp.ui.BaseActivity
import com.example.androidcourseshpp.ui.screens.auth.model.RegistrationData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>
    (ActivityAuthBinding::inflate), RegistrationDataHolder {
    private var registrationData = RegistrationData("", "")

    override fun saveRegistrationData(registrationData: RegistrationData) {
        this.registrationData = registrationData
    }

    override fun getRegistrationData() = registrationData

}
