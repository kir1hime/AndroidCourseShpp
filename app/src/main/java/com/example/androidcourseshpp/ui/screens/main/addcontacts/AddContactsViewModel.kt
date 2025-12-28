package com.example.androidcourseshpp.ui.screens.main.addcontacts


import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.models.userlist.UserItem
import com.example.androidcourseshpp.data.models.userlist.UsersRepository
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactsViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) :
    BaseViewModel<AddContactsContract.Event, AddContactsContract.Effect, AddContactsContract.UIState>() {

    override fun initState() = AddContactsContract.UIState(
        userList = emptyList(),
        isProgressBarShowed = false,
        isTryAgainButtonShowed = false
    )

    init {
        viewModelScope.launch {
            usersRepository.userList.collect { userList ->
                setState { copy(userList = userList) }
            }
        }
    }

    override fun handleEvent(event: AddContactsContract.Event) {
        when (event) {
            is AddContactsContract.Event.LoadUserList -> loadUsers()
            is AddContactsContract.Event.OnSearchButtonClicked -> onSearchButtonClicked()
            is AddContactsContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is AddContactsContract.Event.OnUserItemClicked -> navigateToDetailsScreen(
                event.userItem
            )

            is AddContactsContract.Event.OnAddContactClicked -> addContact(
                event.userItem,
                event.interruptProgressBar
            )
        }
    }

    private fun onSearchButtonClicked() {}
    private fun navigateToPreviousScreen() {
        setEffect(AddContactsContract.Effect.NavigateToContactListScreen)
    }

    private fun addContact(userItem: UserItem, interruptLoading: () -> Unit) {

        processNetworkExceptions(
            toExecute = {
                usersRepository.addContact(userItem)
            },
            processBackendException = {
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setEffect(AddContactsContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { interruptLoading() }
        )
    }

    private fun navigateToDetailsScreen(userItem: UserItem) {
        setEffect(AddContactsContract.Effect.NavigateToDetailsScreen(userItem))
    }

    private fun loadUsers() {
        processNetworkExceptions(
            toExecute = {
                setState {
                    copy(
                        isProgressBarShowed = true,
                        isTryAgainButtonShowed = false,
                        userList = emptyList()
                    )
                }
                usersRepository.initUserList()
                setState { copy(userList = usersRepository.userList.value) }
            },
            processBackendException = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(AddContactsContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }
}