package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(private val dataProvider: DataProvider) : ViewModel() {

    private val _savedEMail = MutableStateFlow(getUserEMail())
    val savedEMail : StateFlow<String> get() = _savedEMail

    fun deleteUserInfo() {
        dataProvider.deleteUserInfo()
    }

    private fun getUserEMail(): String {
        return dataProvider.getUserEMail()
    }
}