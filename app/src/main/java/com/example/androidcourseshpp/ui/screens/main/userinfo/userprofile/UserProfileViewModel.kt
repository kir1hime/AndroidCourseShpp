package com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile


import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.service.user.UserService
import com.example.androidcourseshpp.data.userdata.DEFAULT_AVATAR_VALUE
import com.example.androidcourseshpp.data.userdata.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.userdata.UserDataProvider
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userDataProvider: UserDataProvider,
    private val jwtManager: JWTManager,
    private val userService: UserService
) :
    BaseViewModel<UserProfileContract.Event, UserProfileContract.Effect, UserProfileContract.UIState>() {

    override fun initState() = UserProfileContract.UIState(
        userName = "",
        career = "",
        address = "",
        avatar = "",
        userServerId = DEFAULT_ID_VALUE
    )

    override fun handleEvent(event: UserProfileContract.Event) {
        when (event) {
            is UserProfileContract.Event.OnViewMyContactsButtonClicked -> navigateToMyContacts()
            is UserProfileContract.Event.OnLogOutButtonClicked -> logOut()
            is UserProfileContract.Event.OnEditProfileClicked -> navigateToEditProfileScreen()
            is UserProfileContract.Event.UpdateUserInfo -> updateUserInfo(event.userServerId)
        }

    }

    private fun updateUserInfo(userServerId: Long) {
        viewModelScope.launch {
            processNetworkExceptions(
                toExecute = {
                    val response = userService.getUser(userServerId)
                    val userInfo = response.user

                    val savedAvatarUrl = userDataProvider.getUserAvatarUrl()

                    setState {
                        copy(
                            userName = userInfo.name ?: "",
                            career = userInfo.career ?: "",
                            address = userInfo.address ?: "",
                            avatar = if (savedAvatarUrl != DEFAULT_AVATAR_VALUE) {
                                savedAvatarUrl
                            } else {
                                userInfo.image ?: ""
                            },
                            userServerId = userServerId
                        )
                    }
                },
                processBackendException = {
                    setEffect(UserProfileContract.Effect.ShowToast(R.string.enter_error))
                },
                processResponseProcessingException = {
                    setEffect(UserProfileContract.Effect.ShowToast(R.string.enter_error))
                },
                processConnectionException = {
                    setEffect(UserProfileContract.Effect.ShowToast(R.string.connection_error))
                },
                finally = {}
            )
        }
    }

    private fun navigateToEditProfileScreen() {
        setEffect(UserProfileContract.Effect.NavigateToEditProfileScreen(state.value.userServerId))
    }


    private fun logOut() {
        jwtManager.clearTokens()
        userDataProvider.clearUserServerId()
        userDataProvider.clearUserAvatarUrl()
        setEffect(UserProfileContract.Effect.NavigateToSignInScreen)
    }

    private fun navigateToMyContacts() {
        setEffect(UserProfileContract.Effect.NavigateToContactList)
    }
}