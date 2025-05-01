package com.example.androidcourseshpp.ui.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidcourseshpp.ui.dataStore.DataStore
import com.example.androidcourseshpp.ui.screens.auth.SignUpViewModel
import com.example.androidcourseshpp.ui.screens.main.MyProfileViewModel

class ViewModelFactory(private val dataStore: DataStore) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            SignUpViewModel::class.java ->{
                SignUpViewModel(dataStore)
            }
            MyProfileViewModel::class.java ->{
                MyProfileViewModel(dataStore)
            }
            else ->{
                throw IllegalStateException("Input viewModel isn't existing")
            }
        }

        return viewModel as T
    }
}

fun AppCompatActivity.factory() = ViewModelFactory(DataStore(this))
