package com.example.androidcourseshpp.ui.screens.auth.splash

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.user.GetUserRememberStateUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserUseCase
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.model.toUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserRememberStateUseCase: GetUserRememberStateUseCase,
    private val getUserInfoUseCase: GetUserUseCase
) :
    BaseViewModel<SplashContract.Event, SplashContract.Effect, SplashContract.Sate>() {

    override fun initState() = SplashContract.Sate

    override fun handleEvent(event: SplashContract.Event) {}

    init {
        val isUserSaved = getUserRememberStateUseCase()
        if (isUserSaved) {
            enterToAccount()
        } else {
            setEffect(SplashContract.Effect.NavigateToSignInScreen)
        }
    }

    private fun enterToAccount() {
        executeUseCase(
            toExecute = {
                getUserInfoUseCase()
            },
            onSuccess = { userInfo ->
                setEffect(
                    SplashContract.Effect.NavigateToUserProfileScreen(userInfo.toUserModel())
                )
            },
            onNetworkError = { error ->
                val toastMessageId = if (error == DataError.NetworkError.CONNECTION_ERROR) {
                    R.string.connection_error
                } else {
                    R.string.generic_error
                }
                setEffect(SplashContract.Effect.ShowToast(toastMessageId))
            },
            onError = { setEffect(SplashContract.Effect.NavigateToSignInScreen) }
        )
    }
}