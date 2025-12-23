package com.example.androidcourseshpp.ui.screens.auth.splash

import com.example.androidcourseshpp.data.dataProvider.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.ServicesProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userdataProvider: UserDataProvider,
    private val servicesProvider: ServicesProvider,
) :
    BaseViewModel<SplashContract.Event, SplashContract.Effect, SplashContract.Sate>() {

    override fun initState() = SplashContract.Sate

    override fun handleEvent(event: SplashContract.Event) {

    }

    init {
        val userServerId = userdataProvider.getUserServerId()

        if (userServerId == DEFAULT_ID_VALUE) {
            setEffect(SplashContract.Effect.NavigateToSignInScreen)
        } else {
            enterToAccount(userServerId)
        }
    }

    private fun enterToAccount(userServerId: Long) {
        processNetworkExceptions(
            toExecute = {
                val response = servicesProvider.getUserService().getUser(userServerId)
                val userServerId = response.user.id

                setEffect(
                    SplashContract.Effect.NavigateToMyProfileScreen(userServerId)
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
            processAuthenticationException = {
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            },
            finally = { }
        )
    }
}