package com.example.androidcourseshpp.ui.screens.main.addcontacts


import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class AddContactsViewModel : BaseViewModel<AddContactsContract.Event, AddContactsContract.Effect, AddContactsContract.UIState>() {
    override fun initState()= AddContactsContract.UIState(emptyList())

    override fun handleEvent(event: AddContactsContract.Event) {
        TODO("Not yet implemented")
    }
}