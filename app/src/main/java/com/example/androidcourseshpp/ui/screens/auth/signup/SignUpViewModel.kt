package com.example.androidcourseshpp.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.data.SignUpValidator
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.PasswordErrorMessagesContainer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val dataProvider: DataProvider) : ViewModel() {
    var state = initDefaultState()
        private set

    var savedEmail = getUserEMail()
        private set

    private val _events = Channel<SignUpEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private var passwordChecks: List<(s: String) -> Boolean> = listOf()

    /**
     * function checks all types of password checks and returns certain error text
     * */
    private fun definePasswordErrorMessage(
        inputPassword: String,
    ): Int {

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
                return PasswordErrorMessagesContainer.getMessageResourceIds()[index]
            }
        }

        return R.string.no_error
    }

    private fun initDefaultState() =
        SignUpUiState(
            eMailHelperTextResId = R.string.no_error,
            passwordHelperTextResId = R.string.no_error,
        )

    fun getUserEMail(): String {
        return dataProvider.getUserEMail()
    }

    private fun saveUserInfo(eMail: String, password: String) {
        dataProvider.saveUserInfo(eMail, password)
    }

    private fun deleteUserInfo() {
        dataProvider.deleteUserInfo()
    }

    fun processInputData(email: String, password: String, rememberUserData: Boolean) {
        var isPasswordCorrect = false
        var isEMailCorrect = false

        state = if (!checkEmail(email)) state.copy(
            eMailHelperTextResId = R.string.email_error,
        ) else {
            isEMailCorrect = true
            state.copy(
                eMailHelperTextResId = R.string.no_error,
            )
        }

        state = if (!checkPassword(password))
            state.copy(
                passwordHelperTextResId = definePasswordErrorMessage(password),
            ) else {
                isPasswordCorrect = true
            state.copy(
                passwordHelperTextResId = R.string.no_error,
            )
        }

        if (rememberUserData) {
            saveUserInfo(email, password)
        } else {
            deleteUserInfo()
        }

        if (isEMailCorrect && isPasswordCorrect) {
          viewModelScope.launch {
              _events.send(SignUpEvent.ToSignUpExtended)
          }
        }
    }

    private fun checkEmail(email: String) =
        SignUpValidator.isEMailCorrect(email)


    private fun checkPassword(password: String) =
        SignUpValidator.isPasswordCorrect(password)

}