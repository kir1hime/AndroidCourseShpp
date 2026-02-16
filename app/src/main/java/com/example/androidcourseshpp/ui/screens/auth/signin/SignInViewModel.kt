package com.example.androidcourseshpp.ui.screens.auth.signin


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.usecase.auth.SignInUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.model.toUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
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
            is SignInContract.Event.OnLoginButtonClicked -> signIn(
                email = event.email,
                password = event.password,
                toRememberUser = event.toRememberUser
            )
        }
    }

    private fun signIn(email: String, password: String, toRememberUser: Boolean) {
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                signInUseCase(
                    signInInfo = SignInInfo(email = email, password = password),
                    toRememberUser = toRememberUser
                )
            },
            onSuccess = { userInfo ->
                setState {
                    copy(
                        eMailHelperTextResId = R.string.no_error,
                        passwordHelperTextResId = R.string.no_error
                    )
                }
                setEffect(SignInContract.Effect.NavigateToUserProfileScreen(userInfo.toUserModel()))
            },
            onBackendError = {
                setState {
                    copy(
                        eMailHelperTextResId = R.string.incorrect_input_data,
                        passwordHelperTextResId = R.string.incorrect_input_data
                    )
                }
                setEffect(SignInContract.Effect.ShowToast(R.string.backend_error))
            },
            onConnectionError = { setEffect(SignInContract.Effect.ShowToast(R.string.connection_error)) },
            onResponseProcessingError = { setEffect(SignInContract.Effect.ShowToast(R.string.server_response_error)) },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )

    }

    private fun navigateToSignUpScreen() {
        setEffect(SignInContract.Effect.NavigateToSignUpScreen)
    }
}