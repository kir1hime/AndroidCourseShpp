package com.example.androidcourseshpp.data

import android.util.Patterns

const val MIN_NUM_OF_CHARS_IN_PASSWORD = 8

object SignUpValidator {


    private const val SPECIAL_SYMBOLS = " !#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\""

    fun isEMailCorrect(inputEMail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(inputEMail).matches()
    }

    fun isPasswordCorrect(inputPassword: String): Boolean {
        val passwordChecks: MutableList<(s: String) -> Boolean> =
            mutableListOf(
                SignUpValidator::checkPasswordForNumOfLetters,
                SignUpValidator::checkPasswordForUpperCase,
                SignUpValidator::checkPasswordForLowerCase,
                SignUpValidator::checkPasswordForSpecialSymbols,
                SignUpValidator::checkPasswordForNumbers,
            )

        passwordChecks.forEach { check -> if (!check.invoke(inputPassword)) return false }

        return true
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