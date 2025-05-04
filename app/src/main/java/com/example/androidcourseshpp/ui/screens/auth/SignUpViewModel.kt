package com.example.androidcourseshpp.ui.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.*
import com.example.androidcourseshpp.data.SignUpValidator
import com.example.androidcourseshpp.data.dataProvider.DataProvider


class SignUpViewModel(private val dataProvider: DataProvider) : ViewModel() {

    private val mutableSavedEMail = MutableLiveData<String>()
    val savedEMail get() = mutableSavedEMail

    init {
        savedEMail.value = getUserEMail()
    }

    /**
     * function checks all types of password checks and returns certain error text
     * */
    fun definePasswordErrorMessage(inputPassword: String): String {
        var errorMessage = ""

        with(dataProvider.context) {

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

    private fun getUserEMail(): String {
        return dataProvider.getUserEMail()
    }

    fun saveUserInfo(eMail: String, password: String) {
        dataProvider.saveUserInfo(eMail, password)
    }
}