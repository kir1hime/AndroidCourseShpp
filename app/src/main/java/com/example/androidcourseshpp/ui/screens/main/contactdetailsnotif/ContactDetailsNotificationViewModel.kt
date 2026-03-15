package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactUseCase
import com.example.androidcourseshpp.ui.BaseViewModel
import com.example.androidcourseshpp.ui.notifications.NotificationAction
import com.example.androidcourseshpp.ui.notifications.NotificationService
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel
import com.example.androidcourseshpp.ui.sync.ContactsSyncScheduler
import com.example.androidcourseshpp.ui.utils.executeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsNotificationViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val notificationService: NotificationService,
    private val contactListSyncScheduler: ContactsSyncScheduler,
) : BaseViewModel<ContactDetailsNotificationContract.Event, ContactDetailsNotificationContract.Effect, ContactDetailsNotificationContract.UIState>() {


    override fun initState() = ContactDetailsNotificationContract.UIState

    override fun handleEvent(event: ContactDetailsNotificationContract.Event) {
        when (event) {
            is ContactDetailsNotificationContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is ContactDetailsNotificationContract.Event.OnAddContactButtonClicked -> addContact(
                event.contactDetails
            )

            is ContactDetailsNotificationContract.Event.OnDeleteContactButtonClicked -> deleteContact(
                event.contactDetails
            )
        }
    }

    private fun navigateToPreviousScreen() {
        setEffect(ContactDetailsNotificationContract.Effect.NavigateToPreviousScreen)
    }

    private fun addContact(contactDetails: ContactDetailsModel) {
        executeUseCase(
            toExecute = { addContactUseCase(contactDetails.toContactInfo()) },
            onSuccess = {
                contactListSyncScheduler.executeOnceSyncToRemote()
                setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.add_contact_toast_message))
                setEffect(
                    ContactDetailsNotificationContract.Effect.MainActionWasExecuted
                )
                notificationService.showContactAddedNotification(
                    userInfo = contactDetails,
                    notificationActionId = NotificationAction.ADD_CONTACT.ordinal
                )
            },
            onLocalStorageError = {
                setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.generic_error))
            }
        )
    }

    private fun deleteContact(contactDetails: ContactDetailsModel) {
        executeUseCase(
            toExecute = { deleteContactUseCase(contactDetails.id) },
            onSuccess = {
                contactListSyncScheduler.executeOnceSyncToRemote()
                setEffect(
                    ContactDetailsNotificationContract.Effect.ShowToast(R.string.delete_contact_toast_message)
                )
                setEffect(
                    ContactDetailsNotificationContract.Effect.MainActionWasExecuted
                )
                notificationService.showContactAddedNotification(
                    userInfo = contactDetails,
                    notificationActionId = NotificationAction.ADD_CONTACT.ordinal
                )
            },
            onLocalStorageError = {
                setEffect(ContactDetailsNotificationContract.Effect.ShowToast(R.string.generic_error))
            }
        )
    }
}