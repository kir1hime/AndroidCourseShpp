package com.example.androidcourseshpp.ui.screens.auth.signupextended

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.network.RepositoryProviderHolder
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.repository.user.entity.UpdateUserData
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PHONE_NUMBER_LENGTH = 15

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(private val jwtManager: JWTManager) :
    BaseViewModel<SignUpExtendedContract.Event, SignUpExtendedContract.Effect, SignUpExtendedContract.UIState>() {

    override fun initState(): SignUpExtendedContract.UIState = SignUpExtendedContract.UIState(
        userNameHelperResId = R.string.no_error,
        mobilePhoneHelperResId = R.string.no_error,
        isProgressBarShowed = false
    )

    override fun handleEvent(event: SignUpExtendedContract.Event) {
        when (event) {
            is SignUpExtendedContract.Event.OnForwardButtonClicked -> processInputData(
                event.userName,
                event.mobilePhone,
                event.email,
                event.serverUserId
            )

            is SignUpExtendedContract.Event.OnAddProfilePhotoImageViewClicked -> navigateToChooseProfilePhotoDialog()
            is SignUpExtendedContract.Event.OnCancelButtonClicked -> navigateToPreviousScreen()
        }
    }

    fun processInputData(userName: String, mobilePhone: String, email: String, serverUserId: Long) {
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

        viewModelScope.launch {
            if (isMobilePhoneCorrect && isUserNameCorrect) {
                Log.d("myTag", "aldsf")

                setState { copy(isProgressBarShowed = true) }
                RepositoryProviderHolder(jwtManager).repositoryProvider.getUserRepository()
                    .updateUserInfo(
                        serverUserId,
                        UpdateUserData(name = userName, phone = mobilePhone)
                    )

                setEffect(SignUpExtendedContract.Effect.NavigateToMyProfileScreen(email))



                setState { copy(isProgressBarShowed = false) }

            }
        }
    }

    fun navigateToChooseProfilePhotoDialog() {
        setEffect(SignUpExtendedContract.Effect.NavigateToChooseProfilePhotoDialog)
    }

    fun navigateToPreviousScreen() {
        setEffect(SignUpExtendedContract.Effect.NavigateToPreviousScreen)
    }
}
