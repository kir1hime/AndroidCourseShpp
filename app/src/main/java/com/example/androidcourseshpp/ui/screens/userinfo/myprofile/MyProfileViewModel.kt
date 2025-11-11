package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import android.util.Log
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val userDataProvider: UserDataProvider
) :
    BaseViewModel<MyProfileContract.Event, MyProfileContract.Effect, MyProfileContract.UIState>() {

    override fun initState() = MyProfileContract.UIState("")

    override fun handleEvent(event: MyProfileContract.Event) {
        when (event) {
            is MyProfileContract.Event.OnViewMyContactsButtonClicked -> navigateToMyContacts()
            is MyProfileContract.Event.OnLogOutButtonClicked -> logOut()
            is MyProfileContract.Event.SetUserName -> setUserName(event.name)
        }

    }

    private fun setUserName(name: String) {
        setState { copy(userName = name) }
    }

    private fun logOut() {
        userDataProvider.clearUserName()
        setEffect(MyProfileContract.Effect.NavigateToSignInScreen)
    }

    private fun navigateToMyContacts() {
        setEffect(MyProfileContract.Effect.NavigateToContactList)
    }
}