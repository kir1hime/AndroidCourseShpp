package com.example.androidcourseshpp.ui.screens.main

import androidx.lifecycle.ViewModel
import com.example.androidcourseshpp.ui.dataStore.DataStore

class MyProfileViewModel(private val dataStore: DataStore) : ViewModel() {

    fun deleteUserInfo(eMailKey : String, passwordKey : String){
        dataStore.deleteStringData(eMailKey)
        dataStore.deleteStringData(passwordKey)
    }

    fun getUserEMail(eMailKey: String): String {
        return dataStore.getStringData(eMailKey)
    }
}