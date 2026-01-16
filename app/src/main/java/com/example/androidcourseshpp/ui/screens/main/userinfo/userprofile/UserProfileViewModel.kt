package com.example.androidcourseshpp.ui.screens.main.userinfo.userprofile


import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.auth.ClearTokensUseCase
import com.example.androidcourseshpp.domain.usecase.gallery.ClearGalleryPhotosUseCase
import com.example.androidcourseshpp.domain.usecase.userlocal.ClearUserAvatarUseCase
import com.example.androidcourseshpp.domain.usecase.userlocal.ClearUserServerIdUseCase
import com.example.androidcourseshpp.domain.usecase.userlocal.GetUserAvatarUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserAvatarUseCase: GetUserAvatarUseCase,
    private val clearUserAvatarUseCase: ClearUserAvatarUseCase,
    private val clearUserServerIdUseCase: ClearUserServerIdUseCase,
    private val clearGalleryPhotosUseCase: ClearGalleryPhotosUseCase,
    private val clearTokensUseCase: ClearTokensUseCase
) :
    BaseViewModel<UserProfileContract.Event, UserProfileContract.Effect, UserProfileContract.UIState>() {

    override fun initState() = UserProfileContract.UIState(
        UserUIModel(
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

    private fun setUserInfo(userInfo: UserUIModel) {
        viewModelScope.launch {
            processNetworkExceptions(
                toExecute = {
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
        clearTokensUseCase()
        clearUserAvatarUseCase()
        clearUserServerIdUseCase()
        clearGalleryPhotosUseCase()
        setEffect(UserProfileContract.Effect.NavigateToSignInScreen)
    }

    private fun navigateToMyContacts() {
        setEffect(UserProfileContract.Effect.NavigateToContactList)
    }
}