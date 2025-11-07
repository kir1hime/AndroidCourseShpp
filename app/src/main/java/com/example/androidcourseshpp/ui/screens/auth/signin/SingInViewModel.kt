package com.example.androidcourseshpp.ui.screens.auth.signin

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.BaseViewModel


class SingInViewModel() :
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
            SignInContract.Event.OnLoginButtonClicked -> loginUser()
            SignInContract.Event.OnSignUpLabelClicked -> navigateToSignUpScreen()
        }
    }

    private fun loginUser() {

    }

    private fun navigateToSignUpScreen() {
        setEffect(SignInContract.Effect.NavigateToSingUpScreen)
    }
}