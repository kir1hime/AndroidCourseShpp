package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import com.example.androidcourseshpp.data.EmailParser
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(private val dataProvider: DataProvider) :
    BaseViewModel<MyProfileContract.Event, MyProfileContract.Effect, MyProfileContract.UIState>() {

    fun getSavedEmail(): String {
        return dataProvider.getUserEMail()
    }

    override fun initState() = MyProfileContract.UIState("")

    override fun handleEvent(event: MyProfileContract.Event) {
        when (event) {
            is MyProfileContract.Event.OnViewMyContactsButtonClicked -> viewMyContacts()
            is MyProfileContract.Event.OnLogOutButtonClicked -> logOut()
            is MyProfileContract.Event.UserNameUpdated -> updateUserName(event.userName)
        }

    }

    private fun updateUserName(name: String) {
        setState {
            if (name != "") {
                copy(name)
            } else {
                copy(EmailParser.parseEMail(getSavedEmail()))
            }
        }
    }

    private fun logOut() {
        dataProvider.deleteUserInfo()
        setEffect(MyProfileContract.Effect.NavigateToSignUpScreen)
    }

    private fun viewMyContacts() {
        setEffect(MyProfileContract.Effect.NavigateToContactList)
    }
}