package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import com.example.androidcourseshpp.R
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
                contactId = event.contactId
            )

            is ContactDetailsNotificationContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
        }
    }

    private fun navigateToPreviousScreen() {
        setEffect(ContactDetailsNotificationContract.Effect.NavigateToPreviousScreen)
    }

    private fun executeMainAction(action: NotificationAction, contactId: Int) {
        processNetworkExceptions(
            toExecute = {
                when (action) {
                    NotificationAction.ADD_CONTACT -> {
                        addContactUseCase(contactId)
                        setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.add_contact_toast_message))
                    }

                    NotificationAction.DELETE_CONTACT -> {
                        deleteContactUseCase(contactId)
                        setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.delete_contact_toast_message))
                    }
                }
            },
            processBackendException = {
                setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { }
        )
    }
}