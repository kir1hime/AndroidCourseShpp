package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.user.AddContactUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.ContactListContract
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsNotificationViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val deleteContactUseCase: AddContactUseCase
) : BaseViewModel<ContactDetailsNotificationContract.Event, ContactDetailsNotificationContract.Effect, ContactDetailsNotificationContract.UIState>() {


    override fun initState() = ContactDetailsNotificationContract.UIState

    override fun handleEvent(event: ContactDetailsNotificationContract.Event) {
        when (event) {
            is ContactDetailsNotificationContract.Event.MainActionButtonClicked -> executeMainAction(
                action = event.action,
                contactDetails = event.contactDetails
            )
        }
    }

    private fun executeMainAction(action: NotificationAction, contactDetails: ContactDetailsModel) {
        when (action) {
            NotificationAction.ADD_CONTACT -> addContact(contactDetails)
            NotificationAction.DELETE_CONTACT -> deleteContact(contactDetails)
        }
    }

    private fun addContact(contactDetails: ContactDetailsModel) {
        processNetworkExceptions(
            toExecute = {

                addContactUseCase(contactDetails.toContactInfo())
            },
            processBackendException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processResponseProcessingException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.generic_error))
            },
            processConnectionException = {
                setEffect(ContactListContract.Effect.ShowToast(R.string.connection_error))
            },
            finally = { }
        )
    }

    private fun deleteContact(contactDetails: ContactDetailsModel) {

    }
}