package com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile


import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.domain.usecase.auth.LogOutUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserAvatarUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.model.UserModel
import com.example.androidcourseshpp.ui.sync.ContactListSyncScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserAvatarUseCase: GetUserAvatarUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val contactListSyncScheduler: ContactListSyncScheduler
) :
    BaseViewModel<UserProfileContract.Event, UserProfileContract.Effect, UserProfileContract.UIState>() {

    override fun initState() = UserProfileContract.UIState(
        UserModel(
            id = -1,
            name = "",
            mobilePhone = "",
            address = "", career = "",
            dateOfBirthday = null,
            avatar = ""
        )

    )

    override fun handleEvent(event: UserProfileContract.Event) {
        when (event) {
            is UserProfileContract.Event.OnViewMyContactsButtonClicked -> navigateToMyContacts()
            is UserProfileContract.Event.OnLogOutButtonClicked -> logOut()
            is UserProfileContract.Event.OnEditProfileClicked -> navigateToEditProfileScreen()
            is UserProfileContract.Event.SetUserInfo -> setUserInfo(event.userInfo)
        }

    }

    private fun setUserInfo(userInfo: UserModel) {
        val savedAvatarUrl = getUserAvatarUseCase()
        setState {
            copy(
                userInfo = userInfo.copy(
                    avatar = if (savedAvatarUrl != "") {
                        savedAvatarUrl
                    } else {
                        userInfo.avatar
                    }
                )
            )
        }
    }

    private fun navigateToEditProfileScreen() {
        setEffect(UserProfileContract.Effect.NavigateToEditProfileScreen(state.value.userInfo))
    }


    private fun logOut() {
        viewModelScope.launch {
            contactListSyncScheduler.executeOnceSync()
            logOutUseCase()
            setEffect(UserProfileContract.Effect.NavigateToSignInScreen)
        }
    }

    private fun navigateToMyContacts() {
        setEffect(UserProfileContract.Effect.NavigateToContactList)
    }
}