package com.example.androidcourseshpp.ui.screens.auth

import com.example.androidcourseshpp.ui.screens.auth.model.RegistrationData

interface RegistrationDataHolder {
    fun saveRegistrationData(registrationData: RegistrationData)
    fun getRegistrationData(): RegistrationData
}
