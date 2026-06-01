package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.graphics.Bitmap
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.network.entity.signup.SignUpData
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.service.auth.AuthService
import com.example.androidcourseshpp.data.userdata.UserDataProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.auth.signup.entity.SignUpUserInfoEntity
import com.example.androidcourseshpp.ui.utils.ImageConvertor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val PHONE_NUMBER_LENGTH = 15

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(
    private val authService: AuthService,
    private val jwtManager: JWTManager,
    private val userDataProvider: UserDataProvider,
    private val imageConvertor: ImageConvertor
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
            is SignUpExtendedContract.Event.OnForwardButtonClicked -> processInputData(
                userName = event.userName,
                mobilePhone = event.mobilePhone,
                event.signUpUserInfo,
                event.avatar
            )
        }
    }

    private fun processInputData(
        userName: String,
        mobilePhone: String,
        signUpUserInfo: SignUpUserInfoEntity,
        avatar: Bitmap
    ) {
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


        if (isMobilePhoneCorrect && isUserNameCorrect) {
            processNetworkExceptions(
                toExecute = {
                    setState { copy(isProgressBarShowed = true) }

                    val response = authService.signUp(
                        SignUpData(
                            userName = userName,
                            mobilePhone = mobilePhone,
                            email = signUpUserInfo.email,
                            password = signUpUserInfo.password,
                            image = imageConvertor.convertBitmapToMultipartBody(avatar)
                        )
                    )

                    jwtManager.saveTokens(response.accessToken, response.refreshToken)

                    val userServerId = response.user.id
                    if (signUpUserInfo.toRememberUser) {
                        userDataProvider.saveUserServerId(userServerId)
                    }
                    setEffect(SignUpExtendedContract.Effect.NavigateToUserProfileScreen(userServerId))

                },
                processBackendException = {
                    setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.email_already_registered_error))
                },
                processResponseProcessingException = {
                    setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.server_response_error))
                },
                processConnectionException = {
                    setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.connection_error))
                },
                finally = {
                    setState { copy(isProgressBarShowed = false) }
                }
            )
        }
    }

    private fun navigateToChooseProfilePhotoDialog() {
        setEffect(SignUpExtendedContract.Effect.NavigateToChooseProfilePhotoDialog)
    }

    private fun navigateToPreviousScreen() {
        setEffect(SignUpExtendedContract.Effect.NavigateToPreviousScreen)
    }
}
