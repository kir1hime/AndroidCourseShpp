package com.example.androidcourseshpp.ui.screens.auth.signupextended

import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PHONE_NUMBER_LENGTH = 15

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(val dataProvider: DataProvider) :
    BaseViewModel<SignUpExtendedContract.Event, SignUpExtendedContract.Effect, SignUpExtendedContract.UIState>() {

    override fun initState(): SignUpExtendedContract.UIState = SignUpExtendedContract.UIState(
        userNameHelperResId = R.string.no_error,
        mobilePhoneHelperResId = R.string.no_error
    )

    override fun handleEvent(event: SignUpExtendedContract.Event) {
        when (event) {
            is SignUpExtendedContract.Event.OnForwardButtonClicked -> processInputData(
                event.userName,
                event.mobilePhone
            )

            is SignUpExtendedContract.Event.OnAddProfilePhotoImageViewClicked -> navigateToChooseProfilePhotoDialog()
            is SignUpExtendedContract.Event.OnCancelButtonClicked -> navigateToPreviousScreen()
        }
    }

    fun processInputData(userName: String, mobilePhone: String) {
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
                setEffect(SignUpExtendedContract.Effect.NavigateToMyProfileScreen(dataProvider.getUserEMail()))
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
