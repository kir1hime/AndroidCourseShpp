package com.example.androidcourseshpp.ui.screens.main.addcontacts


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.network.entity.User
import com.example.androidcourseshpp.data.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.userlist.UserItem
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

    override fun initState() = AddContactsContract.UIState(
        userList = emptyList(),
        isProgressBarShowed = false,
        isTryAgainButtonShowed = false
    )

    override fun handleEvent(event: AddContactsContract.Event) {
        when (event) {
            is AddContactsContract.Event.OnAddContactClicked -> addContact(
                event.userItem,
                event.interruptProgressBar
            )

            is AddContactsContract.Event.OnTryAgainButtonClicked -> initUsers()

            is AddContactsContract.Event.OnSearchButtonClicked -> onSearchButtonClicked()
            is AddContactsContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is AddContactsContract.Event.OnUserItemClicked -> navigateToDetailsScreen(
                event.userItem
            )
        }
    }

    private fun onSearchButtonClicked() {}
    private fun navigateToPreviousScreen() {
        setEffect(AddContactsContract.Effect.NavigateToContactListScreen)
    }

    private fun addContact(userItem: UserItem, interruptLoading: () -> Unit) {
        val userServerId = userDataProvider.getUserServerId()
        processNetworkExceptions(
            toExecute = {
                serviceProviderHolder.serviceProvider.getContactsService()
                    .addContact(ContactData(userServerId, userItem.id))

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

    private fun initUsers() {
        var userList = emptyList<User>()
        var contactList = emptyList<User>()
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true, isTryAgainButtonShowed = false) }

                coroutineScope {
                    val usersResponse =
                        async { serviceProviderHolder.serviceProvider.getUserService().getUsers() }
                    val contactsResponse = async {
                        serviceProviderHolder.serviceProvider.getContactsService()
                            .getUserContacts(userDataProvider.getUserServerId())
                    }
                    userList = usersResponse.await().users
                    contactList = contactsResponse.await().contacts
                }

                val userItemList = userList.map { user ->
                    UserItem(
                        id = user.id,
                        name = user.name ?: "",
                        career = user.career ?: "",
                        avatarURL = user.image ?: "",
                        isContact =contactList.contains(user)
                    )
                }


                setState { copy(userList = userItemList) }

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