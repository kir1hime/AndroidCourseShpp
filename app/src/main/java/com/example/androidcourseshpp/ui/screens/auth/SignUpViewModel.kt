package com.example.androidcourseshpp.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.SignUpValidator
import com.example.androidcourseshpp.ui.dataStore.DataStore


class SignUpViewModel(private val dataStore: DataStore) : ViewModel() {

    private companion object {
        const val MIN_NUM_OF_CHARS_IN_PASSWORD = 8
        const val EMAIL_KEY = "userEMail"
        const val PASSWORD_KEY = "userPassword"
    }


    /**
     * function checks all types of password checks and returns certain error text
     * */
    fun definePasswordErrorMessage(inputPassword: String): String {
        var errorMessage = ""

        with(dataStore.context) {

            val passwordChecks: MutableList<(s: String) -> Boolean> =
                mutableListOf(
                    SignUpValidator::checkPasswordForNumOfLetters,
                    SignUpValidator::checkPasswordForUpperCase,
                    SignUpValidator::checkPasswordForLowerCase,
                    SignUpValidator::checkPasswordForSpecialSymbols,
                    SignUpValidator::checkPasswordForNumbers,
                )
            val passwordErrorMessages: MutableList<String> =
                mutableListOf(
                    getString(R.string.less_8_symbols_pswd_error, MIN_NUM_OF_CHARS_IN_PASSWORD),
                    getString(R.string.capital_letter_error),
                    getString(R.string.lowercase_letter_error),
                    getString(R.string.special_symbol_error),
                    getString(R.string.numbers_error),
                )

            for ((index, check) in passwordChecks.withIndex()) {
                if (!check.invoke(inputPassword)) {
                    errorMessage = passwordErrorMessages[index]
                }
            }
        }

        return errorMessage
    }

    fun isExistingAccount( eMailKey: String): Boolean {
        return dataStore.getStringData(eMailKey) != ""
    }

    fun getUserEMail( eMailKey: String): String {
        return dataStore.getStringData(eMailKey)
    }

     fun saveUserInfo( eMail: String, password: String) {
        dataStore.putStringData(EMAIL_KEY, eMail)
        dataStore.putStringData(PASSWORD_KEY, password)
    }
}