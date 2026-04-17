package com.example.androidcourseshpp.ui.screens.editprofile


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.dataProvider.DEFAULT_AVATAR_VALUE
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.entity.user.UpdateUserData
import com.example.androidcourseshpp.data.network.service.user.UserService
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userService: UserService,
    private val userDataProvider: UserDataProvider
) :
    BaseViewModel<EditProfileContract.Event, EditProfileContract.Effect, EditProfileContract.UIState>() {

    override fun initState(): EditProfileContract.UIState {
        return (EditProfileContract.UIState(
            userName = "",
            mobilePhone = "",
            career = "",
            address = "",
            dateOfBirthday = null,
            avatar = "",
            isProgressBarShowed = false,
            isSaveButtonEnabled = true
        ))
    }

    override fun handleEvent(event: EditProfileContract.Event) {
        when (event) {
            is EditProfileContract.Event.OnAddProfilePhotoImageViewClicked -> navigateToChooseProfilePhotoDialog()
            is EditProfileContract.Event.OnSaveButtonClicked -> {
                updateUserInfo(event.userServerId, event.updateUserData)
                navigateToMyProfileScreen()
            }

            is EditProfileContract.Event.SetUserInfo -> setUserInfo(event.userServerId)
            is EditProfileContract.Event.ProfilePhotoUpdated -> updateProfilePhoto(event.profilePhotoUrl)
            is EditProfileContract.Event.UserNameUpdated -> updateUserName(event.userName)
            is EditProfileContract.Event.CareerUpdated -> updateCareer(event.career)
            is EditProfileContract.Event.AddressUpdated -> updateAddress(event.address)
            is EditProfileContract.Event.MobilePhoneUpdated -> updateMobilePhone(event.mobilePhone)
            is EditProfileContract.Event.DateOfBirthdayUpdated -> updateDateOfBirthday(event.dateOfBirthday)
        }
    }

    private fun updateUserName(userName: String) {
        setState { copy(userName = userName) }
    }

    private fun updateCareer(career: String) {
        setState { copy(career = career) }
    }

    private fun updateMobilePhone(mobilePhone: String) {
        setState { copy(mobilePhone = mobilePhone) }
    }

    private fun updateAddress(address: String) {
        setState { copy(address = address) }
    }

    private fun updateDateOfBirthday(dateOfBirthday: Date?) {
        setState { copy(dateOfBirthday = dateOfBirthday) }
    }

    private fun updateProfilePhoto(profilePhotoUrl: String) {
        userDataProvider.saveUserAvatarUrl(profilePhotoUrl)
        setState { copy(avatar = profilePhotoUrl) }
    }

    private fun setUserInfo(userServerId: Long) {
        processNetworkExceptions(
            toExecute = {
                setState {
                    copy(isProgressBarShowed = true)
                }
                val response = userService.getUser(userServerId)
                val userInfo = response.user

                val savedAvatarUrl = userDataProvider.getUserAvatarUrl()

                with(userInfo) {
                    setState {
                        copy(
                            userName = name ?: "",
                            career = userInfo.career ?: "",
                            mobilePhone = phone ?: "",
                            address = userInfo.address ?: "",
                            dateOfBirthday = birthday,
                            avatar = if (savedAvatarUrl != DEFAULT_AVATAR_VALUE) {
                                savedAvatarUrl
                            } else {
                                userInfo.image ?: ""
                            }
                        )
                    }
                }
            },
            processBackendException = {
                setEffect(EditProfileContract.Effect.ShowToast(R.string.generic_error))
                disableSaveButton()
            },
            processAuthenticationException = {
                setEffect(EditProfileContract.Effect.ShowToast(R.string.generic_error))
                disableSaveButton()
            },
            processResponseProcessingException = {
                setEffect(EditProfileContract.Effect.ShowToast(R.string.generic_error))
                disableSaveButton()
            },
            processConnectionException = {
                setEffect(EditProfileContract.Effect.ShowToast(R.string.connection_error))
                disableSaveButton()
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun updateUserInfo(userServerId: Long, updateUserData: UpdateUserData) {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                userService.updateUserInfo(userServerId, updateUserData)
            },
            processBackendException = {
                setEffect(EditProfileContract.Effect.ShowToast(R.string.generic_error))
            },
            processAuthenticationException = {
                setEffect(EditProfileContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setEffect(EditProfileContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setEffect(EditProfileContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun disableSaveButton() {
        setState { copy(isSaveButtonEnabled = false) }
    }

    private fun navigateToMyProfileScreen() {
        setEffect(EditProfileContract.Effect.NavigateToMyProfileScreen)
    }

    private fun navigateToChooseProfilePhotoDialog() {
        setEffect(EditProfileContract.Effect.NavigateToChooseProfilePhotoDialog)
    }
}