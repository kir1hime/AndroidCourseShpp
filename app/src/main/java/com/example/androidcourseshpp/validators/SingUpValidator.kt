package com.example.androidcourseshpp.validators

import android.content.Context
import android.util.Patterns
import com.example.androidcourseshpp.R

private const val SPECIAL_SYMBOLS = " !#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\""
private const val MIN_NUM_OF_CHARS_IN_PSWD = 8

object SignUpValidator {


    fun checkEMail(inputEMail: String, context: Context): String {
        return if (!Patterns.EMAIL_ADDRESS.matcher(inputEMail).matches())
            context.getString(R.string.email_error) else ""
    }

    fun checkPasswordInput(inputPassword: String, context: Context): String {
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

    private fun checkForNumOfLetters(inPswd: String, context: Context): String {
        return if (inPswd.length < MIN_NUM_OF_CHARS_IN_PSWD)
            context.getString(
                R.string.less_8_symbols_pswd_error,
                MIN_NUM_OF_CHARS_IN_PSWD
            ) else ""
    }

    private fun checkForUpperCase(inPswd: String, context: Context): String {
        return if (inPswd.any { it.isUpperCase() }) ""
        else context.getString(R.string.capital_letter_error)
    }

    private fun checkForLowerCase(inPswd: String, context: Context): String {
        return if (inPswd.any { it.isLowerCase() }) ""
        else context.getString(R.string.lowercase_letter_error)
    }

    private fun checkForSpecialSymbols(inPswd: String, context: Context): String {
        return if (inPswd.any { SPECIAL_SYMBOLS.contains(it) }) ""
        else context.getString(R.string.special_symbol_error)
    }

    private fun checkForNumbers(inPswd: String, context: Context): String {
        return if (inPswd.any { it.isDigit() }) ""
        else context.getString(R.string.numbers_error)
    }
}