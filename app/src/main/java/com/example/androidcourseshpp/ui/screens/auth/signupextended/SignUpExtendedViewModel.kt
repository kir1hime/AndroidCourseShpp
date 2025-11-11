package com.example.androidcourseshpp.ui.screens.auth.signupextended

import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.network.service.BackendException
import com.example.androidcourseshpp.data.network.service.ConnectionException
import com.example.androidcourseshpp.data.network.service.ProcessResponseException
import com.example.androidcourseshpp.data.network.service.user.entity.UpdateUserData
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PHONE_NUMBER_LENGTH = 15

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(
    private val serviceProviderHolder: RetrofitServiceProviderHolder,
    private val userDataProvider: UserDataProvider
) :
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
            is SignUpExtendedContract.Event.SaveUserName -> saveUserName(event.name)
        }
    }

    private fun saveUserName(name: String) {
        userDataProvider.saveUserName(name)
    }

    private fun processInputData(
        userName: String,
        mobilePhone: String,
        email: String,
        serverUserId: Long
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

        viewModelScope.launch {
            if (isMobilePhoneCorrect && isUserNameCorrect) {

                try {
                    setState { copy(isProgressBarShowed = true) }

                    serviceProviderHolder.serviceProvider.getUserService()
                        .updateUserInfo(
                            serverUserId,
                            UpdateUserData(name = userName, phone = mobilePhone)
                        )

                    setEffect(SignUpExtendedContract.Effect.NavigateToMyProfileScreen(email))
                } catch (e: BackendException) {
                    setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.backend_error))
                } catch (e: ProcessResponseException) {
                    setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.server_response_error))
                } catch (e: ConnectionException) {
                    setEffect(SignUpExtendedContract.Effect.ShowToast(R.string.connection_error))
                } finally {
                    setState { copy(isProgressBarShowed = false) }

                }
            }
        }
    }

    private fun navigateToChooseProfilePhotoDialog() {
        setEffect(SignUpExtendedContract.Effect.NavigateToChooseProfilePhotoDialog)
    }

    private fun navigateToPreviousScreen() {
        setEffect(SignUpExtendedContract.Effect.NavigateToPreviousScreen)
    }
}
