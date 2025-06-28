package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.dataProvider.DataProvider

class MyProfileViewModel(private val dataProvider: DataProvider) : ViewModel() {

    private val mutableSavedEMail = MutableLiveData<String>()
    val savedEMail get() = mutableSavedEMail

    init {
        savedEMail.value = getUserEMail()
    }

    fun deleteUserInfo() {
        dataProvider.deleteUserInfo()
    }

    private fun getUserEMail(): String {
        return dataProvider.getUserEMail()
    }
}