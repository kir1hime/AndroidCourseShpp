package com.example.androidcourseshpp.ui.screens.main.addcontacts

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUsersUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.notifications.NotificationAction
import com.example.androidcourseshpp.ui.notifications.NotificationService
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.UserItem
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.toUserItem
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel
import com.example.androidcourseshpp.ui.utils.isContainsOrderedSequence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddContactsViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val notificationService: NotificationService
) :
    BaseViewModel<AddContactsContract.Event, AddContactsContract.Effect, AddContactsContract.UIState>() {

    override fun initState() = AddContactsContract.UIState(
        userList = emptyList(),
        isProgressBarShowed = false,
        isTryAgainButtonShowed = false,
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
                userInfo = event.contactInfo,
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
            if (user.name.isContainsOrderedSequence(input)) {
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
        setEffect(AddContactsContract.Effect.NavigateToContactListScreen)
    }


    private fun addContact(
        userInfo: ContactDetailsModel,
        interruptSuccessLoading: () -> Unit,
        interruptFailureLoading: () -> Unit
    ) {
        executeUseCase(
            toExecute = {
                addContactUseCase(userInfo.toContactInfo())
            },
            onSuccess = {
                interruptSuccessLoading()
                notificationService.showContactAddedNotification(
                    userInfo = userInfo,
                    notificationActionId = NotificationAction.ADD_CONTACT.ordinal
                )
            },
            onLocalStorageError = {
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
                interruptFailureLoading()
            }
        )
    }

    private fun navigateToDetailsScreen(userItem: UserItem) {
        setEffect(AddContactsContract.Effect.NavigateToDetailsScreen(userItem))
    }

    private fun loadUsers() {
        executeUseCase(
            toExecute = {
                setState {
                    copy(
                        isProgressBarShowed = true,
                        isTryAgainButtonShowed = false,
                        userList = emptyList()
                    )
                }
                getUsersUseCase()
            },
            onSuccess = { userList ->
                setState { copy(userList = userList.map { it.toUserItem() }) }
            },
            onBackendError = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
            },
            onConnectionError = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(AddContactsContract.Effect.ShowToast(R.string.connection_error))

            },
            onResponseProcessingError = {
                setState { copy(isTryAgainButtonShowed = true) }
                setEffect(AddContactsContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )

    }
}