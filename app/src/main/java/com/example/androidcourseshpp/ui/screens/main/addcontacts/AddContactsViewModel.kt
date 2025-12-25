package com.example.androidcourseshpp.ui.screens.main.addcontacts


import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.userlist.UserItem
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.utils.ImageConvertor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactsViewModel @Inject constructor(
    private val serviceProviderHolder: RetrofitServiceProviderHolder,
    private val userDataProvider: UserDataProvider
) :
    BaseViewModel<AddContactsContract.Event, AddContactsContract.Effect, AddContactsContract.UIState>() {

    init {
        initUsers()
    }

    override fun initState() = AddContactsContract.UIState(emptyList(), false)

    override fun handleEvent(event: AddContactsContract.Event) {
        when (event) {
            is AddContactsContract.Event.OnAddContactClicked -> addContact()
            is AddContactsContract.Event.OnSearchButtonClicked -> onSearchButtonClicked()
            is AddContactsContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is AddContactsContract.Event.OnUserItemClicked -> navigateToDetailsScreen(
                event.userItem,
                event.avatar
            )
        }
    }

    private fun onSearchButtonClicked() {}
    private fun navigateToPreviousScreen() {
        setEffect(AddContactsContract.Effect.NavigateToContactListScreen)
    }

    private fun addContact() {

    }

    private fun navigateToDetailsScreen(userItem: UserItem, avatar: ImageView) {
        setEffect(AddContactsContract.Effect.NavigateToDetailsScreen(userItem, avatar))
    }

    private fun initUsers() {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                val response = serviceProviderHolder.serviceProvider.getUserService().getUsers()

                val userItemList = response.users.map { user ->
                    UserItem(
                        id = user.id,
                        name = user.name ?: "",
                        career = user.career ?: "",
                        avatarURL = user.image ?: ""
                    )
                }


                setState { copy(userList = userItemList) }
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
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }
}