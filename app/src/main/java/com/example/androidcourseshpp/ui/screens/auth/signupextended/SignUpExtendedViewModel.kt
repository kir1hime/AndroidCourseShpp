package com.example.androidcourseshpp.ui.screens.auth.signupextended

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val PHONE_NUMBER_LENGTH = 15

class SignUpExtendedViewModel() : ViewModel() {

    private val _state: MutableStateFlow<SignUpExtendedState> = MutableStateFlow(initDefaultState())
    val state: Flow<SignUpExtendedState> = _state.asStateFlow()

    private val _effect = Channel<SignUpExtendedEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun processInputData(userName: String, mobilePhone : String){
        var isMobilePhoneCorrect = false
        var isUserNameCorrect = false

        _state.update {
            if (!userName.isBlank()){
                isUserNameCorrect = true
                _state.value.copy(userNameHelperResId = R.string.no_error)
            } else {
                isUserNameCorrect = false
                _state.value.copy(userNameHelperResId = R.string.empty_name_error)
            }
        }

        _state.update {
            if(mobilePhone.isBlank() || mobilePhone.length != PHONE_NUMBER_LENGTH){
                isMobilePhoneCorrect = false
                _state.value.copy(mobilePhoneHelperResId = R.string.incorrect_mobile_phone_error)
            } else {
                isMobilePhoneCorrect = true
                _state.value.copy(mobilePhoneHelperResId = R.string.no_error)
            }
        }

        viewModelScope.launch {
            if (isMobilePhoneCorrect && isUserNameCorrect) {
                _effect.send(SignUpExtendedEffect.moveToMyProfileScreen)
            }
        }
    }

    private fun initDefaultState() = SignUpExtendedState(
        userNameHelperResId = R.string.no_error,
        mobilePhoneHelperResId = R.string.no_error
    )
}
