package com.example.androidcourseshpp.ui.screens.splash

import android.util.Log
import com.example.androidcourseshpp.data.dataProvider.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.data.network.ServicesProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.UserInfoEntity
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
            setEffect(SplashContract.Effect.NavigateToSignUpScreen)

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
                setEffect(SplashContract.Effect.NavigateToSignUpScreen)
            },
            processResponseProcessingException = {
                setEffect(SplashContract.Effect.NavigateToSignUpScreen)
            },
            processConnectionException = {
                setEffect(SplashContract.Effect.NavigateToSignUpScreen)
            },
            processAuthenticationException = {
                setEffect(SplashContract.Effect.NavigateToSignUpScreen)
            },
            finally = { }
        )
    }
}