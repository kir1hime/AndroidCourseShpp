package com.example.androidcourseshpp.ui.screens.main.contactdetails

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.notifications.NotificationAction
import com.example.androidcourseshpp.ui.notifications.NotificationService
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel
import com.example.androidcourseshpp.ui.utils.executeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val notificationService: NotificationService
) :
    BaseViewModel<ContactDetailsContract.Event, ContactDetailsContract.Effect, ContactDetailsContract.UIState>() {
    override fun initState() = ContactDetailsContract.UIState

    override fun handleEvent(event: ContactDetailsContract.Event) {
        when (event) {
            is ContactDetailsContract.Event.OnAddContactButtonClicked -> addContact(event.contactDetails)
        }
    }

    private fun addContact(contactDetails: ContactDetailsModel) {
        executeUseCase(
            toExecute = { addContactUseCase(contactDetails.toContactInfo()) },
            onSuccess = {
                notificationService.showContactAddedNotification(
                    userInfo = contactDetails,
                    notificationActionId = NotificationAction.ADD_CONTACT.ordinal
                )
                setEffect(ContactDetailsContract.Effect.ShowToast(R.string.add_contact_toast_message))
                setEffect(ContactDetailsContract.Effect.ContactWasAdded)
            },
            onLocalStorageError = {
                setEffect(ContactDetailsContract.Effect.ShowToast(R.string.generic_error))
            }
        )
    }
}