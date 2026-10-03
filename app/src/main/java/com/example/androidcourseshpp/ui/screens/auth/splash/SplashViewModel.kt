package com.example.androidcourseshpp.ui.screens.auth.splash

import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.domain.usecase.user.GetUserRememberStateUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.model.toUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserRememberStateUseCase: GetUserRememberStateUseCase,
    private val getUserInfoUseCase: GetUserUseCase
) : BaseViewModel<SplashContract.Event, SplashContract.Effect, SplashContract.Sate>() {

    override fun initState() = SplashContract.Sate

    override fun handleEvent(event: SplashContract.Event) {}

    init {
        viewModelScope.launch {
            val isUserSaved = getUserRememberStateUseCase() ?: false
            if (isUserSaved) {
                enterToAccount()
            } else {
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            }
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
                setEffect(SplashContract.Effect.ShowToast(error.toMessageResId()))
                setEffect(SplashContract.Effect.NavigateToSignInScreen)
            }
        )
    }
}