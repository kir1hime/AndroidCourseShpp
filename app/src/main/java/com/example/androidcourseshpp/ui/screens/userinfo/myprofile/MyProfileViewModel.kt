package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val dataProvider: DataProvider,
    private val jwtManager: JWTManager
) :
    BaseViewModel<MyProfileContract.Event, MyProfileContract.Effect, MyProfileContract.UIState>() {


    override fun initState() = MyProfileContract.UIState(
        "",
        "",
        "",
        "",
        ""
    )

    override fun handleEvent(event: MyProfileContract.Event) {
        when (event) {
            is MyProfileContract.Event.OnViewMyContactsButtonClicked -> viewMyContacts()
            is MyProfileContract.Event.OnLogOutButtonClicked -> logOut()
            is MyProfileContract.Event.UserInfoUpdated -> updateUserInfo(event.sate)
        }

    }

    private fun updateUserInfo(state: MyProfileContract.UIState) {
        setState {
            with(state) {
                copy(
                    userName = userName,
                    career = career,
                    mobilePhone = mobilePhone,
                    address = address,
                    dateOfBirthday = dateOfBirthday
                )
            }
        }
    }

    private fun logOut() {
        dataProvider.clearUserServerId()
        jwtManager.clearTokens()
        setEffect(MyProfileContract.Effect.NavigateToSignUpScreen)
    }

    private fun viewMyContacts() {
        setEffect(MyProfileContract.Effect.NavigateToContactList)
    }
}