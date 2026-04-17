package com.example.androidcourseshpp.ui.screens.userinfo.myprofile


import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.DEFAULT_AVATAR_VALUE
import com.example.androidcourseshpp.data.dataProvider.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.service.user.UserService
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val userDataProvider: UserDataProvider,
    private val jwtManager: JWTManager,
    private val userService: UserService
) :
    BaseViewModel<MyProfileContract.Event, MyProfileContract.Effect, MyProfileContract.UIState>() {

    override fun initState() = MyProfileContract.UIState(
        userName = "",
        career = "",
        address = "",
        avatar = "",
        userServerId = DEFAULT_ID_VALUE
    )

    override fun handleEvent(event: MyProfileContract.Event) {
        when (event) {
            is MyProfileContract.Event.OnViewMyContactsButtonClicked -> navigateToMyContacts()
            is MyProfileContract.Event.OnLogOutButtonClicked -> logOut()
            is MyProfileContract.Event.OnEditProfileClicked -> navigateToEditProfileScreen()
            is MyProfileContract.Event.UpdateUserInfo -> updateUserInfo(event.userServerId)
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
                    setEffect(MyProfileContract.Effect.ShowToast(R.string.enter_error))
                },
                processAuthenticationException = {
                    setEffect(MyProfileContract.Effect.ShowToast(R.string.enter_error))
                },
                processResponseProcessingException = {
                    setEffect(MyProfileContract.Effect.ShowToast(R.string.enter_error))
                },
                processConnectionException = {
                    setEffect(MyProfileContract.Effect.ShowToast(R.string.connection_error))
                },
                finally = {}
            )
        }
    }

    private fun navigateToEditProfileScreen() {
        setEffect(MyProfileContract.Effect.NavigateToEditProfileScreen(state.value.userServerId))
    }


    private fun logOut() {
        jwtManager.clearTokens()
        userDataProvider.clearUserServerId()
        userDataProvider.clearUserAvatarUrl()
        setEffect(MyProfileContract.Effect.NavigateToSignInScreen)
    }

    private fun navigateToMyContacts() {
        setEffect(MyProfileContract.Effect.NavigateToContactList)
    }
}