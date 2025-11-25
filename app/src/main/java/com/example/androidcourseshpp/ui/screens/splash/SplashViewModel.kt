package com.example.androidcourseshpp.ui.screens.splash

import com.example.androidcourseshpp.data.dataProvider.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.data.network.ServicesProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.UserInfoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataProvider: DataProvider,
    private val servicesProvider: ServicesProvider
) :
    BaseViewModel<SplashContract.Event, SplashContract.Effect, SplashContract.Sate>() {

    override fun initState() = SplashContract.Sate

    override fun handleEvent(event: SplashContract.Event) {

    }

    init {
        val userServerId = dataProvider.getUserServerId()

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
                val userInfo = response.user

                setEffect(
                    SplashContract.Effect.NavigateToMyProfileScreen(
                        UserInfoEntity(
                            userName = userInfo.name ?: "",
                            career = userInfo.career ?: "",
                            address = userInfo.address ?: "",
                            dateOfBirthday = userInfo.birthday ?: "",
                            mobilePhone = userInfo.phone ?: ""
                        )
                    )
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