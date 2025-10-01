package com.example.androidcourseshpp.ui.screens.auth.signup

data class SignUpUiState (
    val isEmailCorrect : Boolean,
    val isPasswordCorrect : Boolean,
    val eMailHelperTextResId : Int,
    val passwordHelperTextResId : Int,
)
