package com.example.androidcourseshpp.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.SignUpValidator
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val dataProvider: DataProvider) : ViewModel() {

    private val mutableSavedEMail = MutableStateFlow(getUserEMail())
    val savedEMail : StateFlow<String> get() = mutableSavedEMail

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
                break
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