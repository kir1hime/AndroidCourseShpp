package com.example.androidcourseshpp.validators

import android.content.Context
import android.util.Patterns
import com.example.androidcourseshpp.R

private const val SPECIAL_SYMBOLS = " !#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\""
private const val MIN_NUM_OF_CHARS_IN_PSWD = 8

object SignUpValidator {

    fun checkEMail(inputEMail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(inputEMail).matches()
    }

  /*  fun checkPasswordInput(inputPassword: String, context: Context): String {
        val passwordChecks: MutableList<(s: String, context: Context) -> String> =
            mutableListOf(
                ::checkForNumOfLetters,
                ::checkForUpperCase,
                ::checkForLowerCase,
                ::checkForSpecialSymbols,
                ::checkForNumbers,
            )

        passwordChecks.forEach { check ->
            val errorText = check.invoke(inputPassword, context)

            if (errorText.isNotEmpty()) {
                return errorText
            }
        }

        return ""

    }
*/
  fun checkForNumOfLetters(inPswd: String): Boolean {
        return inPswd.length > MIN_NUM_OF_CHARS_IN_PSWD
    }

    fun checkForUpperCase(inPswd: String): Boolean {
        return inPswd.any { it.isUpperCase() }
    }

    fun checkForLowerCase(inPswd: String):Boolean {
        return inPswd.any { it.isLowerCase() }
    }

    fun checkForSpecialSymbols(inPswd: String): Boolean {
        return inPswd.any { SPECIAL_SYMBOLS.contains(it) }
    }

    fun checkForNumbers(inPswd: String): Boolean {
        return inPswd.any { it.isDigit() }
    }
}