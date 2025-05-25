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

    private var passwordChecks: List<(s: String) -> Boolean> = listOf()

    /**
     * function checks all types of password checks and returns certain error text
     * */
    fun definePasswordErrorMessage(
        inputPassword: String,
        passwordErrorMessages: List<String>
    ): String {
        var errorMessage = ""

        passwordChecks =
            mutableListOf(
                SignUpValidator::checkPasswordForNumOfLetters,
                SignUpValidator::checkPasswordForUpperCase,
                SignUpValidator::checkPasswordForLowerCase,
                SignUpValidator::checkPasswordForSpecialSymbols,
                SignUpValidator::checkPasswordForNumbers,
            )

        for ((index, check) in passwordChecks.withIndex()) {
            if (!check.invoke(inputPassword)) {
                errorMessage = passwordErrorMessages[index]
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