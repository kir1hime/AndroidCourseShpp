package com.example.androidcourseshpp.validators

import android.util.Patterns

private const val SPECIAL_SYMBOLS = " !#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\""
private const val MIN_NUM_OF_CHARS_IN_PASSWORD = 8

object SignUpValidator {

    fun checkEMail(inputEMail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(inputEMail).matches()
    }

    fun checkPasswordForNumOfLetters(inputPassword: String): Boolean {
        return inputPassword.length > MIN_NUM_OF_CHARS_IN_PASSWORD
    }

    fun checkPasswordForUpperCase(inputPassword: String): Boolean {
        return inputPassword.any { it.isUpperCase() }
    }

    fun checkPasswordForLowerCase(inputPassword: String): Boolean {
        return inputPassword.any { it.isLowerCase() }
    }

    fun checkPasswordForSpecialSymbols(inputPassword: String): Boolean {
        return inputPassword.any { SPECIAL_SYMBOLS.contains(it) }
    }

    fun checkPasswordForNumbers(inputPassword: String): Boolean {
        return inputPassword.any { it.isDigit() }
    }
}