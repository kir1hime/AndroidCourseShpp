package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.notifications.NotificationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsNotificationViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase
) : BaseViewModel<ContactDetailsNotificationContract.Event, ContactDetailsNotificationContract.Effect, ContactDetailsNotificationContract.UIState>() {


    override fun initState() = ContactDetailsNotificationContract.UIState

    override fun handleEvent(event: ContactDetailsNotificationContract.Event) {
        when (event) {
            is ContactDetailsNotificationContract.Event.OnMainActionButtonClicked -> executeMainAction(
                action = event.action,
                contactInfo = event.contactInfo
            )

            is ContactDetailsNotificationContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
        }
    }

    private fun navigateToPreviousScreen() {
        setEffect(ContactDetailsNotificationContract.Effect.NavigateToPreviousScreen)
    }

    private fun executeMainAction(action: NotificationAction, contactInfo: ContactInfo) {
        executeUseCase(
            toExecute = {
                when (action) {
                    NotificationAction.ADD_CONTACT -> addContactUseCase(contactInfo)
                    NotificationAction.DELETE_CONTACT -> deleteContactUseCase(contactInfo.id)
                }
            },
            onSuccess = {
                when (action) {
                    NotificationAction.ADD_CONTACT ->
                        setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.add_contact_toast_message))

                    NotificationAction.DELETE_CONTACT ->
                        setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.delete_contact_toast_message))
                }
            },
            onLocalStorageError = {
                setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.generic_error))
            },
            finally = { }
        )
    }
}