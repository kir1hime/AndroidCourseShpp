package com.example.androidcourseshpp.ui.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(private val dataProvider: DataProvider) : ViewModel() {

    private val mutableSavedEMail = MutableStateFlow(getUserEMail())
    val savedEMail : StateFlow<String> get() = mutableSavedEMail

    fun deleteUserInfo() {
        dataProvider.deleteUserInfo()
    }

    private fun getUserEMail(): String {
        return dataProvider.getUserEMail()
    }
}