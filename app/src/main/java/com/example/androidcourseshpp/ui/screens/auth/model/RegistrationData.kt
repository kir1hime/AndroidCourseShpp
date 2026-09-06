package com.example.androidcourseshpp.ui.screens.auth.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegistrationData(val email: String, val password: String) : Parcelable
