package com.example.androidcourseshpp.ui.screens.userinfo.myprofile

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(private val dataProvider: DataProvider) : ViewModel() {

    fun deleteUserInfo() {
        dataProvider.deleteUserInfo()
    }

     fun getUserEMail(): String {
        return dataProvider.getUserEMail()
    }
}