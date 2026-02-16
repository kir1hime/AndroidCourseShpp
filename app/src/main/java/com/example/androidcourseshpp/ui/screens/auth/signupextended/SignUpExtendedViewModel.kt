package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.graphics.Bitmap
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.domain.usecase.auth.SignUpUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.auth.signup.model.SignUpModel
import com.example.androidcourseshpp.ui.screens.model.toUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val PHONE_NUMBER_LENGTH = 15

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) :
    BaseViewModel<SignUpExtendedContract.Event, SignUpExtendedContract.Effect, SignUpExtendedContract.UIState>() {

    override fun initState(): SignUpExtendedContract.UIState = SignUpExtendedContract.UIState(
        userNameHelperResId = R.string.no_error,
        mobilePhoneHelperResId = R.string.no_error,
        isProgressBarShowed = false
    )

    override fun handleEvent(event: SignUpExtendedContract.Event) {
        when (event) {
            is SignUpExtendedContract.Event.OnAddProfilePhotoImageViewClicked -> navigateToChooseProfilePhotoDialog()
            is SignUpExtendedContract.Event.OnCancelButtonClicked -> navigateToPreviousScreen()
            is SignUpExtendedContract.Event.OnForwardButtonClicked -> signUp(
                userName = event.userName,
                mobilePhone = event.mobilePhone,
                event.signUpUserInfo,
                event.avatar
            )
        }
    }

    private fun signUp(
        userName: String,
        mobilePhone: String,
        signUpUserInfo: SignUpModel,
        avatar: Bitmap
    ) {
        if (!isInputDataCorrect(userName, mobilePhone)) {
            return
        }
        executeUseCase(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                signUpUseCase(
                    SignUpInfo(
                        userName = userName,
                        mobilePhone = mobilePhone,
                        email = signUpUserInfo.email,
                        password = signUpUserInfo.password,
                        avatar = avatar
                    ),
                    toRememberUser = signUpUserInfo.toRememberUser
                )
            },
            onSuccess = { userInfo ->
                setEffect(
                    SignUpExtendedContract.Effect.NavigateToUserProfileScreen(
                        userInfo.toUserModel()
                    )
                )
            },
            onBackendError = { setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.email_already_registered_error)) },
            onConnectionError = { setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.connection_error)) },
            onResponseProcessingError = { setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.server_response_error)) },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun isInputDataCorrect(
        userName: String,
        mobilePhone: String,
    ): Boolean {
        var isMobilePhoneCorrect: Boolean
        var isUserNameCorrect: Boolean

        if (!userName.isBlank()) {
            isUserNameCorrect = true
            setState { copy(userNameHelperResId = R.string.no_error) }
        } else {
            isUserNameCorrect = false
            setState { copy(userNameHelperResId = R.string.empty_name_error) }
        }

        if (mobilePhone.isBlank() || mobilePhone.length != PHONE_NUMBER_LENGTH) {
            isMobilePhoneCorrect = false
            setState { copy(mobilePhoneHelperResId = R.string.incorrect_mobile_phone_error) }
        } else {
            isMobilePhoneCorrect = true
            setState { copy(mobilePhoneHelperResId = R.string.no_error) }
        }

        return isMobilePhoneCorrect && isUserNameCorrect
    }

    private fun navigateToChooseProfilePhotoDialog() {
        setEffect(SignUpExtendedContract.Effect.NavigateToChooseProfilePhotoDialog)
    }

    private fun navigateToPreviousScreen() {
        setEffect(SignUpExtendedContract.Effect.NavigateToPreviousScreen)
    }
}
