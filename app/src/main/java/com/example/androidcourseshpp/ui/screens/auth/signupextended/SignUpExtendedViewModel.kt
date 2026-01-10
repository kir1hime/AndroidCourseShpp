package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.graphics.Bitmap
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.service.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.domain.usecase.auth.SignUpUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserServerIdUseCase
import com.example.androidcourseshpp.domain.usecase.user.SaveUserServerIdUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.auth.signin.SignInContract
import com.example.androidcourseshpp.ui.screens.auth.signupextended.entity.SignUpUserInfoEntity
import com.example.androidcourseshpp.ui.utils.ImageConvertor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val PHONE_NUMBER_LENGTH = 15

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getUserServerIdUseCase: GetUserServerIdUseCase,
    private val saveUserServerIdUseCase: SaveUserServerIdUseCase,
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
        signUpUserInfo: SignUpUserInfoEntity,
        avatar: Bitmap
    ) {
        if (isInputDataCorrect(userName, mobilePhone)) {
            processNetworkExceptions(
                toExecute = {
                    setState { copy(isProgressBarShowed = true) }

                    val userServerId = signUpUseCase(
                        SignUpInfo(
                            userName = userName,
                            mobilePhone = mobilePhone,
                            email = signUpUserInfo.email,
                            password = signUpUserInfo.password,
                            avatar = avatar
                        )
                    )

                    if (signUpUserInfo.toRememberUser) {
                        saveUserServerIdUseCase(userServerId)
                    }

                    setEffect(
                        SignUpExtendedContract.Effect.NavigateToUserProfileScreen(
                            getUserServerIdUseCase()
                        )
                    )
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
