package com.example.androidcourseshpp.ui.utils

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.ui.screens.auth.AuthActivity
import com.example.androidcourseshpp.ui.screens.auth.SignUpViewModel
import com.example.androidcourseshpp.ui.screens.contacts.ContactListFragment
import com.example.androidcourseshpp.ui.screens.contacts.ContactListViewModel
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import com.example.androidcourseshpp.ui.screens.main.MyProfileViewModel

class ViewModelFactory(
    private val dataProvider: DataProvider?,
    private val contentResolver: ContentResolver?,
    private val isAccessToContactsAllowed: Boolean = false
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            SignUpViewModel::class.java -> {
                SignUpViewModel(dataProvider!!)
            }

            MyProfileViewModel::class.java -> {
                MyProfileViewModel(dataProvider!!)
            }

            ContactListViewModel::class.java -> {
                ContactListViewModel(contentResolver!!, isAccessToContactsAllowed)
            }

            else -> {
                throw IllegalStateException("Input viewModel isn't existing")
            }
        }

        return viewModel as T
    }
}

fun AuthActivity.factory() = ViewModelFactory(DataProvider(applicationContext), null )
fun MainActivity.factory() = ViewModelFactory(DataProvider(applicationContext), null)
fun ContactListFragment.factory() = ViewModelFactory(null, requireActivity().contentResolver)

