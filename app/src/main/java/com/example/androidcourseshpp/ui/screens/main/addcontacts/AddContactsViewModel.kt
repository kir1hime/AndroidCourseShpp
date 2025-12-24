package com.example.androidcourseshpp.ui.screens.main.addcontacts


import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.userlist.UserItem
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactsViewModel @Inject constructor(
    private val serviceProviderHolder: RetrofitServiceProviderHolder,
    private val userDataProvider: UserDataProvider
) :
    BaseViewModel<AddContactsContract.Event, AddContactsContract.Effect, AddContactsContract.UIState>() {

    override fun initState() = AddContactsContract.UIState(emptyList(), false)

    override fun handleEvent(event: AddContactsContract.Event) {
        TODO("Not yet implemented")
    }

    init {
        initUsers()
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
            processBackendException = {},
            processConnectionException = {},
            processAuthenticationException = {},
            processResponseProcessingException = {},
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }
}