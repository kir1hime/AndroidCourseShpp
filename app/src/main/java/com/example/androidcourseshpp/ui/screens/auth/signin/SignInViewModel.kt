package com.example.androidcourseshpp.ui.screens.auth.signin


import android.util.Log
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.DEFAULT_USER_SERVER_ID_VALUE
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.service.auth.entity.SignInData
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val jwtManager: JWTManager,
    private val serviceProviderHolder: RetrofitServiceProviderHolder,
    private val userDataProvider: UserDataProvider
) :
    BaseViewModel<SignInContract.Event, SignInContract.Effect, SignInContract.UIState>() {


    init {
        val userServerId = userDataProvider.getUserServerId()

        if (userServerId != DEFAULT_USER_SERVER_ID_VALUE) {
            enterToAccount(userServerId)
        }
    }


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

                val userInfo = response.user

                if (toRememberUser) {
                    userDataProvider.saveUserServerId(userInfo.id)
                }

                setEffect(SignInContract.Effect.NavigateToMyProfileScreen(userInfo.toUserInfoEntity()))
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
            processUserUnauthorizedException = {
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

    private fun enterToAccount(userServerId: Long) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                Log.d("myTag", userServerId.toString())
                Log.d("myTag", jwtManager.getAccessToken().toString())
                val userInfo =
                    serviceProviderHolder.serviceProvider.getUserService().getUser(userServerId)
                setEffect(SignInContract.Effect.NavigateToMyProfileScreen(userInfo.toUserInfoEntity()))
            },
            processBackendException = {
                setEffect(SignInContract.Effect.ShowToast(R.string.backend_error))
            },
            processResponseProcessingException = {
                setEffect(SignInContract.Effect.ShowToast(R.string.server_response_error))
            },
            processConnectionException = {
                setEffect(SignInContract.Effect.ShowToast(R.string.connection_error))
            },
            processUserUnauthorizedException = {
                setEffect(SignInContract.Effect.ShowToast(R.string.unauthorized_error))
            },
            finally = {
                setState {
                    copy(
                        isProgressBarShowed = false,
                    )
                }
            }
        )
    }

    private fun navigateToSignUpScreen() {
        setEffect(SignInContract.Effect.NavigateToSingUpScreen)
    }
}