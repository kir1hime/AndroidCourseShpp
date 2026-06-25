package com.example.androidcourseshpp.ui.screens.main.addcontacts

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUsersUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.UserItem
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.toUserItem
import com.example.androidcourseshpp.ui.utils.containsOrderedSequence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddContactsViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val getUsersUseCase: GetUsersUseCase
) :
    BaseViewModel<AddContactsContract.Event, AddContactsContract.Effect, AddContactsContract.UIState>() {

    override fun initState() = AddContactsContract.UIState(
        userList = emptyList(),
        isProgressBarShowed = false,
        isTryAgainButtonShowed = false,
        isContactListChanged = false,
        isSearchMode = false
    )

    init {
        loadUsers()
    }

    private val _filteredUserList = MutableStateFlow<List<UserItem>>(emptyList())
    val filteredUserList get() = _filteredUserList

    override fun handleEvent(event: AddContactsContract.Event) {
        when (event) {
            is AddContactsContract.Event.OnArrowTopFloatingButtonClicked -> scrollUserListToTop()
            is AddContactsContract.Event.SearchModeSwitched -> switchSearchMode(event.isSearchModeEnabled)
            is AddContactsContract.Event.OnHideSearchButtonClicked -> hideSearchBar()
            is AddContactsContract.Event.LoadUserList -> loadUsers()
            is AddContactsContract.Event.OnSearchButtonClicked -> showSearchBar()
            is AddContactsContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is AddContactsContract.Event.OnSearchBarTextChanged -> updateFilteredUserListBy(event.input)
            is AddContactsContract.Event.OnUserItemClicked -> navigateToDetailsScreen(
                event.userItem
            )

            is AddContactsContract.Event.OnAddContactClicked -> addContact(
                userId = event.userId,
                interruptSuccessLoading = event.interruptSuccessLoading,
                interruptFailureLoading = event.interruptFailureLoading
            )
        }
    }

    private fun scrollUserListToTop() {
        setEffect(AddContactsContract.Effect.ScrollUserListToTop)
    }

    private fun switchSearchMode(isSearchMode: Boolean) {
        setState { copy(isSearchMode = isSearchMode) }
    }

    private fun updateFilteredUserListBy(input: String) {
        val filteredContactList = mutableListOf<UserItem>()
        state.value.userList.forEach { user ->
            if (user.name.containsOrderedSequence(input)) {
                filteredContactList.add(user)
            }
        }
        _filteredUserList.value = filteredContactList
    }

    private fun showSearchBar() {
        setEffect(AddContactsContract.Effect.ShowSearchBar)
    }

    private fun hideSearchBar() {
        setEffect(AddContactsContract.Effect.HideSearchBar)
    }

    private fun navigateToPreviousScreen() {
        setEffect(AddContactsContract.Effect.NavigateToContactListScreen(state.value.isContactListChanged))
    }


    private fun addContact(
        userId: Long,
        interruptSuccessLoading: () -> Unit,
        interruptFailureLoading: () -> Unit
    ) {

        processNetworkExceptions(
            toExecute = {
                addContactUseCase(userId)
                setState { copy(isContactListChanged = true) }
                interruptSuccessLoading()
            },
            processBackendException = {
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
                interruptFailureLoading()
            },
            processConnectionException = {
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
                interruptFailureLoading()
            },
            processResponseProcessingException = {
                setEffect(AddContactsContract.Effect.ShowToast(R.string.connection_error))
                interruptFailureLoading()
            },
            finally = { }
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
                val userList = getUsersUseCase().map { it.toUserItem() }

                setState { copy(userList = userList) }
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