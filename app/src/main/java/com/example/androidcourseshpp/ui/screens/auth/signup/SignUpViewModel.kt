package com.example.androidcourseshpp.ui.screens.auth.signup

import com.example.androidcourseshpp.data.SignUpValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.PasswordErrorMessagesContainer
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpData
import com.example.androidcourseshpp.ui.BaseViewModel

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val jwtManager: JWTManager,
    private val serviceProviderHolder: RetrofitServiceProviderHolder
) :
    BaseViewModel<SignUpContract.Event, SignUpContract.Effect, SignUpContract.UIState>() {

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
                event.rememberUserData
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
            signUpUser(email, password, toRememberUser)
        }
    }

    private fun signUpUser(email: String, password: String, toRememberUser: Boolean) {

        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }

                val response =
                    serviceProviderHolder.serviceProvider.getAuthService()
                        .signUp(SignUpData(email, password))

                jwtManager.saveAccessToken(response.accessToken)
                jwtManager.saveRefreshToken(response.refreshToken)

                val serverUserId = response.user.id

                if (toRememberUser){

                }

                setEffect(
                    SignUpContract.Effect.NavigateToSignUpExtendedScreen(
                        serverUserId,
                        email,
                        password,
                        toRememberUser
                    )
                )
            },
            processBackendException = {
                setState { copy(eMailHelperTextResId = R.string.email_already_registered_error) }
                setEffect(SignUpContract.Effect.ShowToast(R.string.backend_error))
            },
            processResponseProcessingException = {
                setEffect(SignUpContract.Effect.ShowToast(R.string.server_response_error))
            },
            processConnectionException = {
                setEffect(SignUpContract.Effect.ShowToast(R.string.connection_error))
            },
            processUserUnauthorizedException = {},
            finally = {
                setState { copy(isProgressBarShowed = false) }
            }
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