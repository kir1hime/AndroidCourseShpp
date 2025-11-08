package com.example.androidcourseshpp.ui.screens.auth.signin

import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.service.BackendException
import com.example.androidcourseshpp.data.network.service.ConnectionException
import com.example.androidcourseshpp.data.network.service.ProcessResponseException
import com.example.androidcourseshpp.data.network.service.auth.entity.SignInData
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.auth.signup.SignUpContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val jwtManager: JWTManager,
    private val serviceProviderHolder: RetrofitServiceProviderHolder
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
                password = event.password
            )

            is SignInContract.Event.OnSignUpLabelClicked -> navigateToSignUpScreen()
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                setState { copy(isProgressBarShowed = true) }

                val response = serviceProviderHolder.serviceProvider.getAuthService()
                    .singIn(SignInData(email = email, password = password))

                jwtManager.saveAccessToken(response.accessToken)
                jwtManager.saveRefreshToken(response.refreshToken)

                val userName = response.user.name ?: ""
                setEffect(SignInContract.Effect.NavigateToMyProfileScreen(userName))

            } catch (e: BackendException) {
                setState { copy(eMailHelperTextResId = R.string.incorrect_email_or_password_error) }
                setEffect(SignInContract.Effect.ShowToast(R.string.backend_error))

            } catch (e: ProcessResponseException) {
                setEffect(SignInContract.Effect.ShowToast(R.string.server_response_error))

            } catch (e: ConnectionException) {
                setEffect(SignInContract.Effect.ShowToast(R.string.connection_error))

            } finally {
                setState {
                    copy(
                        isProgressBarShowed = false,
                        eMailHelperTextResId = R.string.no_error
                    )
                }
            }
        }
    }


    private fun navigateToSignUpScreen() {
        setEffect(SignInContract.Effect.NavigateToSingUpScreen)
    }
}