package com.example.androidcourseshpp.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity
import com.example.androidcourseshpp.ui.screens.auth.SignUpViewModel
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import com.example.androidcourseshpp.ui.screens.main.MyProfileViewModel

class ViewModelFactory(private val dataProvider: DataProvider) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            SignUpViewModel::class.java ->{
                SignUpViewModel(dataProvider)
            }
            MyProfileViewModel::class.java ->{
                MyProfileViewModel(dataProvider)
            }
            else ->{
                throw IllegalStateException("Input viewModel isn't existing")
            }
        }

        return viewModel as T
    }
}

fun AuthActivity.factory() = ViewModelFactory(DataProvider(applicationContext))
fun MainActivity.factory() = ViewModelFactory(DataProvider(applicationContext))

