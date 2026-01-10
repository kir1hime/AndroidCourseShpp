package com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile


import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.source.local.userdata.DEFAULT_AVATAR_VALUE
import com.example.androidcourseshpp.data.source.local.userdata.DEFAULT_ID_VALUE
import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.domain.entity.UserInfo
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userDataProvider: UserDataProvider,
    private val jwtManager: JWTManager,
    private val serviceProvider: ServicesProvider
) :
    BaseViewModel<UserProfileContract.Event, UserProfileContract.Effect, UserProfileContract.UIState>() {

    override fun initState() = UserProfileContract.UIState(
        UserInfo(-1, "", "", "", "", null, "")

    )

    override fun handleEvent(event: UserProfileContract.Event) {
        when (event) {
            is UserProfileContract.Event.OnViewMyContactsButtonClicked -> navigateToMyContacts()
            is UserProfileContract.Event.OnLogOutButtonClicked -> logOut()
            is UserProfileContract.Event.OnEditProfileClicked -> navigateToEditProfileScreen()
            is UserProfileContract.Event.SetUserInfo -> setUserInfo(event.userInfo)
        }

    }

    private fun setUserInfo(userInfo: UserInfo) {
        viewModelScope.launch {
            processNetworkExceptions(
                toExecute = {
                    val savedAvatarUrl = userDataProvider.getUserAvatarUrl()

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
        setEffect(UserProfileContract.Effect.NavigateToEditProfileScreen(state.value.userInfo))
    }


    private fun logOut() {
        jwtManager.clearTokens()
        userDataProvider.clearUserServerId()
        userDataProvider.clearUserAvatarUrl()
        userDataProvider.clearGalleryPhotos()
        setEffect(UserProfileContract.Effect.NavigateToSignInScreen)
    }

    private fun navigateToMyContacts() {
        setEffect(UserProfileContract.Effect.NavigateToContactList)
    }
}