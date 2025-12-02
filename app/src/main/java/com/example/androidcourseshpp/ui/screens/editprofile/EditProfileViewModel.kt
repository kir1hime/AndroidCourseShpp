package com.example.androidcourseshpp.ui.screens.editprofile


import com.example.androidcourseshpp.data.network.ServicesProvider
import com.example.androidcourseshpp.data.network.service.user.entity.UpdateUserData
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val servicesProvider: ServicesProvider) :
    BaseViewModel<EditProfileContract.Event, EditProfileContract.Effect, EditProfileContract.UIState>() {

    override fun initState(): EditProfileContract.UIState {
        return (EditProfileContract.UIState(
            userName = "",
            mobilePhone = "",
            career = "",
            address = "",
            dateOfBirthday = null,
            avatar = "",
            isProgressBarShowed = false
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
        }
    }

    private fun setUserInfo(userServerId: Long) {
        processNetworkExceptions(
            toExecute = {
                setState {
                    copy(isProgressBarShowed = true)
                }
                val response = servicesProvider.getUserService().getUser(userServerId)
                val userInfo = response.user

                with(userInfo) {
                    setState {
                        copy(
                            userName = name ?: "",
                            career = userInfo.career ?: "",
                            mobilePhone = phone ?: "",
                            address = userInfo.address ?: "",
                            dateOfBirthday = birthday,
                            avatar = image ?: "",
                        )
                    }
                }
            },
            processBackendException = {},
            processAuthenticationException = {},
            processConnectionException = {}, processResponseProcessingException = {},
            finally = {
                setState {
                    copy(isProgressBarShowed = false)
                }
            }
        )
    }

    private fun updateUserInfo(userServerId: Long, updateUserData: UpdateUserData) {
        processNetworkExceptions(
            toExecute = {
                setState {
                    copy(
                        isProgressBarShowed = true,
                        userName = updateUserData.name ?: "",
                        career = updateUserData.career ?: "",
                        address = updateUserData.address ?: "",
                        mobilePhone = updateUserData.phone ?: "",
                        dateOfBirthday = updateUserData.birthday,
                    )
                }

                servicesProvider.getUserService().updateUserInfo(userServerId, updateUserData)
            },
            processBackendException = {},
            processAuthenticationException = {},
            processConnectionException = {},
            processResponseProcessingException = {},
            finally = {
                setState {
                    copy(isProgressBarShowed = false)
                }
            }
        )

    }


    private fun navigateToMyProfileScreen() {
        setEffect(EditProfileContract.Effect.NavigateToMyProfileScreen(state.value))
    }

    private fun navigateToChooseProfilePhotoDialog() {
        setEffect(EditProfileContract.Effect.NavigateToChooseProfilePhotoDialog)
    }


}