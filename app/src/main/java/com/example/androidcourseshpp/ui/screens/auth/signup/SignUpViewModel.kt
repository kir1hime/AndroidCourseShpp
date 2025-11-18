package com.example.androidcourseshpp.ui.screens.auth.signup

import com.example.androidcourseshpp.data.SignUpValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.PasswordErrorMessagesContainer
import com.example.androidcourseshpp.data.dataProvider.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.data.network.ServicesProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.SignUpUserInfo
import com.example.androidcourseshpp.ui.screens.UserInfoEntity


@HiltViewModel
class SignUpViewModel @Inject constructor(
    dataProvider: DataProvider,
    private val servicesProvider: ServicesProvider
) :
    BaseViewModel<SignUpContract.Event, SignUpContract.Effect, SignUpContract.UIState>() {

    init {
        val userServerId = dataProvider.getUserServerId()
        if (userServerId != DEFAULT_ID_VALUE) {
            enterToAccount(userServerId)
        }
    }

    override fun initState() = SignUpContract.UIState(
        eMailHelperTextResId = R.string.no_error,
        passwordHelperTextResId = R.string.no_error,
        false
    )

    private var passwordChecks: List<(s: String) -> Boolean> = listOf()

    override fun handleEvent(event: SignUpContract.Event) {
        when (event) {
            is SignUpContract.Event.OnResisterButtonClicked -> processInputData(
                event.email,
                event.password,
                event.toRememberUser
            )
        }
    }

    private fun processInputData(email: String, password: String, toRememberUser: Boolean) {
        var isPasswordCorrect: Boolean
        var isEMailCorrect: Boolean

        if (!checkEmail(email)) {
            isEMailCorrect = false
            setState {
                copy(eMailHelperTextResId = R.string.incorrect_email_error)
            }
        } else {
            isEMailCorrect = true
            setState {
                copy(
                    eMailHelperTextResId = R.string.no_error,
                )
            }
        }

        if (!checkPassword(password)) {
            isPasswordCorrect = false
            setState {
                copy(passwordHelperTextResId = definePasswordErrorMessage(password))
            }
        } else {
            isPasswordCorrect = true
            setState {
                copy(
                    passwordHelperTextResId = R.string.no_error,
                )
            }
        }

        if (isEMailCorrect && isPasswordCorrect) {
            setEffect(
                SignUpContract.Effect.NavigateToSignUpExtended(
                    SignUpUserInfo(
                        email = email,
                        password = password,
                        toRememberUser = toRememberUser
                    )
                )
            )
        }
    }

    private fun enterToAccount(userServerId: Long) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }

                val response = servicesProvider.getUserService().getUser(userServerId)
                val userInfo = response.user

                setEffect(
                    SignUpContract.Effect.NavigateToMyProfileScreen(
                        UserInfoEntity(
                            userName = userInfo.name ?: "",
                            career = userInfo.career ?: "",
                            address = userInfo.address ?: "",
                            dateOfBirthday = userInfo.birthday ?: "",
                            mobilePhone = userInfo.phone ?: ""
                        )
                    )
                )

            },
            processBackendException = {
                setEffect(SignUpContract.Effect.ShowToast(R.string.backend_error))
            },
            processResponseProcessingException = {
                setEffect(SignUpContract.Effect.ShowToast(R.string.server_response_error))
            },
            processConnectionException = {
                setEffect(SignUpContract.Effect.ShowToast(R.string.connection_error))
            },
            processAuthenticationException = {
                setEffect(SignUpContract.Effect.ShowToast(R.string.backend_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

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

    private fun checkEmail(email: String) =
        SignUpValidator.isEMailCorrect(email)


    private fun checkPassword(password: String) =
        SignUpValidator.isPasswordCorrect(password)
}