package com.example.androidcourseshpp.ui.screens.auth.splash

import com.example.androidcourseshpp.data.source.local.userdata.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.domain.usecase.user.GetUserServerIdUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserServerIdUseCase: GetUserServerIdUseCase,
    private val servicesProvider: ServicesProvider,
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
        processNetworkExceptions(
            toExecute = {
                val response = servicesProvider.getUserService().getUser(userServerId)

                setEffect(
                    SplashContract.Effect.NavigateToUserProfileScreen(response.user.toUserInfo())
                )

            },
            processBackendException = {
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            },
            processResponseProcessingException = {
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            },
            processConnectionException = {
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            },
            finally = { }
        )
    }
}