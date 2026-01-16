package com.example.androidcourseshpp.ui.screens.main.editprofile


import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.user.UpdateUserInfoUseCase
import com.example.androidcourseshpp.domain.usecase.userlocal.GetUserAvatarUseCase
import com.example.androidcourseshpp.domain.usecase.userlocal.SaveUserAvatarUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getUserAvatarUseCase: GetUserAvatarUseCase,
    private val saveUserAvatarUseCase: SaveUserAvatarUseCase
) :
    BaseViewModel<EditProfileContract.Event, EditProfileContract.Effect, EditProfileContract.UIState>() {

    override fun initState(): EditProfileContract.UIState {
        return (EditProfileContract.UIState(
            UserUIModel(-1, "", "", "", "", null, ""),
            isProgressBarShowed = false,
            isSaveButtonEnabled = true,
            isUserDataChanged = false
        ))
    }

    override fun handleEvent(event: EditProfileContract.Event) {
        when (event) {
            is EditProfileContract.Event.OnAddProfilePhotoImageViewClicked -> navigateToChooseProfilePhotoDialog()
            is EditProfileContract.Event.SetUserInfo -> setUserInfo(event.userInfo)
            is EditProfileContract.Event.ProfilePhotoUpdated -> updateProfilePhoto(event.profilePhotoUrl)
            is EditProfileContract.Event.UserNameUpdated -> updateUserName(event.userName)
            is EditProfileContract.Event.CareerUpdated -> updateCareer(event.career)
            is EditProfileContract.Event.AddressUpdated -> updateAddress(event.address)
            is EditProfileContract.Event.MobilePhoneUpdated -> updateMobilePhone(event.mobilePhone)
            is EditProfileContract.Event.DateOfBirthdayUpdated -> updateDateOfBirthday(event.dateOfBirthday)
            is EditProfileContract.Event.OnSaveButtonClicked -> {
                updateUserInfo()
                navigateToUserProfileScreen()
            }
        }
    }

    private fun updateUserName(userName: String) {
        setState { copy(state.value.userInfo.copy(name = userName), isUserDataChanged = true) }
    }

    private fun updateCareer(career: String) {
        setState { copy(state.value.userInfo.copy(career = career), isUserDataChanged = true) }
    }

    private fun updateMobilePhone(mobilePhone: String) {
        setState {
            copy(
                state.value.userInfo.copy(mobilePhone = mobilePhone),
                isUserDataChanged = true
            )
        }
    }

    private fun updateAddress(address: String) {
        setState { copy(state.value.userInfo.copy(address = address), isUserDataChanged = true) }
    }

    private fun updateDateOfBirthday(dateOfBirthday: Date?) {
        setState {
            copy(
                state.value.userInfo.copy(dateOfBirthday = dateOfBirthday),
                isUserDataChanged = true
            )
        }
    }

    private fun updateProfilePhoto(profilePhotoUrl: String) {
        saveUserAvatarUseCase(profilePhotoUrl)
        setState {
            copy(
                state.value.userInfo.copy(avatar = profilePhotoUrl),
                isUserDataChanged = true
            )
        }
    }

    private fun setUserInfo(userInfo: UserUIModel) {
        processNetworkExceptions(
            toExecute = {
                setState {
                    copy(isProgressBarShowed = true)
                }

                val savedAvatarUrl = getUserAvatarUseCase()

                with(userInfo) {
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
            },
            processBackendException = {
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

    private fun updateUserInfo() {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                if (state.value.isUserDataChanged) {
                    updateUserInfoUseCase(state.value.userInfo.toUserInfo())
                }
            },
            processBackendException = {
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

    private fun navigateToUserProfileScreen() {
        setEffect(
            EditProfileContract.Effect.NavigateToUserProfileScreen(
                state.value.isUserDataChanged,
                state.value.userInfo
            )
        )
    }

    private fun navigateToChooseProfilePhotoDialog() {
        setEffect(EditProfileContract.Effect.NavigateToChooseProfilePhotoDialog)
    }
}