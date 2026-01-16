package com.example.androidcourseshpp.ui.screens.auth.signin


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.usecase.auth.SignInUseCase
import com.example.androidcourseshpp.domain.usecase.userlocal.SaveUserServerIdUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.model.toUserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveUserServerIdUseCase: SaveUserServerIdUseCase,
) :
    BaseViewModel<SignInContract.Event, SignInContract.Effect, SignInContract.UIState>() {

    override fun initState(): SignInContract.UIState {
        return SignInContract.UIState(
            eMailHelperTextResId = R.string.no_error,
            passwordHelperTextResId = R.string.no_error,
            isProgressBarShowed = false
        )
    }

    override fun handleEvent(event: SignInContract.Event) {
        when (event) {
            is SignInContract.Event.OnSignUpLabelClicked -> navigateToSignUpScreen()
            is SignInContract.Event.OnLoginButtonClicked -> logInUser(
                email = event.email,
                password = event.password,
                toRememberUser = event.toRememberUser
            )
        }
    }

    private fun logInUser(email: String, password: String, toRememberUser: Boolean) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                val userInfo = signInUseCase(SignInInfo(email, password)).toUserUIModel()

                if (toRememberUser) {
                    saveUserServerIdUseCase(userInfo.id)
                }

                setEffect(SignInContract.Effect.NavigateToUserProfileScreen(userInfo))
            },
            processBackendException = {
                setState { copy(eMailHelperTextResId = R.string.incorrect_email_or_password_error) }
                setEffect(SignInContract.Effect.ShowToast(R.string.backend_error))
            },
            processResponseProcessingException = {
                setEffect(SignInContract.Effect.ShowToast(R.string.server_response_error))
            },
            processConnectionException = {
                setEffect(SignInContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = {
                setState {
                    copy(
                        isProgressBarShowed = false,
                        eMailHelperTextResId = R.string.no_error
                    )
                }
            }
        )
    }

    private fun navigateToSignUpScreen() {
        setEffect(SignInContract.Effect.NavigateToSignUpScreen)
    }
}