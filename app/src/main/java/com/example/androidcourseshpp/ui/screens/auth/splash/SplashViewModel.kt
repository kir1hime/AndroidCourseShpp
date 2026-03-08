package com.example.androidcourseshpp.ui.screens.auth.splash

import com.example.androidcourseshpp.data.source.local.userdata.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.domain.usecase.user.GetUserUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserServerIdUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.model.toUserModel
import com.example.androidcourseshpp.ui.utils.executeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserServerIdUseCase: GetUserServerIdUseCase,
    private val getUserInfoUseCase: GetUserUseCase
) :
    BaseViewModel<SplashContract.Event, SplashContract.Effect, SplashContract.Sate>() {

    override fun initState() = SplashContract.Sate

    override fun handleEvent(event: SplashContract.Event) {

    }

    init {
        val userServerId = getUserServerIdUseCase()

        if (userServerId == DEFAULT_ID_VALUE) {
            setEffect(SplashContract.Effect.NavigateToSignInScreen)
        } else {
            enterToAccount(userServerId)
        }
    }

    private fun enterToAccount(userServerId: Int) {
        executeUseCase(
            toExecute = {
                getUserInfoUseCase(userServerId)
            },
            onSuccess = { userInfo ->
                setEffect(
                    SplashContract.Effect.NavigateToUserProfileScreen(userInfo.toUserModel())
                )
            },
            onBackendError = {
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            },
            onResponseProcessingError = {
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            },
            onConnectionError = {
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            }
        )
    }
}