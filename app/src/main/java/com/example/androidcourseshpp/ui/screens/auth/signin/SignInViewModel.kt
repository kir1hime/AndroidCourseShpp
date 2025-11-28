package com.example.androidcourseshpp.ui.screens.auth.signin


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.service.auth.entity.SignInData
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.utils.ImageConvertor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val jwtManager: JWTManager,
    private val serviceProviderHolder: RetrofitServiceProviderHolder,
    private val dataProvider: DataProvider,
    private val imageConvertor: ImageConvertor
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
            is SignInContract.Event.OnLoginButtonClicked -> loginUser(
                email = event.email,
                password = event.password,
                toRememberUser = event.toRememberUser
            )

            is SignInContract.Event.OnSignUpLabelClicked -> navigateToSignUpScreen()
        }
    }

    private fun loginUser(email: String, password: String, toRememberUser: Boolean) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }

                val response = serviceProviderHolder.serviceProvider.getAuthService()
                    .singIn(SignInData(email = email, password = password))

                jwtManager.saveAccessToken(response.accessToken)
                jwtManager.saveRefreshToken(response.refreshToken)

                val userServerId = response.user.id

                if (toRememberUser) {
                    dataProvider.saveUserServerId(userServerId)
                }

                setEffect(SignInContract.Effect.NavigateToMyProfileScreen)

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
            processAuthenticationException = {
                setEffect(SignInContract.Effect.ShowToast(R.string.unauthorized_error))
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
        setEffect(SignInContract.Effect.NavigateToSingUpScreen)
    }
}