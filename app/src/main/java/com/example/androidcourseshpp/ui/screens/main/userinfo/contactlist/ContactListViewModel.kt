package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist

import com.example.androidcourseshpp.data.contactlist.ContactsRepository
import com.example.androidcourseshpp.data.contactlist.ContactItem
import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.network.RetrofitServiceProviderHolder
import com.example.androidcourseshpp.data.network.entity.contacts.ContactData
import com.example.androidcourseshpp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.Stack
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val serviceProviderHolder: RetrofitServiceProviderHolder,
    private val userDataProvider: UserDataProvider
) : BaseViewModel<ContactListContract.Event, ContactListContract.Effect, ContactListContract.UIState>() {

    override fun initState() = ContactListContract.UIState(emptyList(), false)
    val deletedItems = Stack<Pair<ContactItem, Int>>()


    override fun handleEvent(event: ContactListContract.Event) {
        when (event) {
            is ContactListContract.Event.OnArrowBackButtonClicked -> navigateToPreviousScreen()
            is ContactListContract.Event.OnAddContactClicked -> navigateToAddContactsScreen()
            is ContactListContract.Event.UpdateContactList -> loadContacts()
            is ContactListContract.Event.ContactItemAdded -> addContactItem(
                event.contactItem,
                event.position
            )

            is ContactListContract.Event.ContactItemDeleted -> deleteContactItem(
                event.contactItem,
                event.position
            )

            is ContactListContract.Event.OnDeleteSelectedItemsFloatingButtonClicked -> deleteListOfContactItems(
                event.contactItems
            )

            is ContactListContract.Event.OnItemClicked -> navigateToDetailsScreen(
                event.contact
            )
        }
    }


    private fun loadContacts() {
        processNetworkExceptions(
            toExecute = {
                setState { copy(isProgressBarShowed = true) }
                val response = serviceProviderHolder.serviceProvider.getContactsService()
                    .getUserContacts(userDataProvider.getUserServerId())

                val contactItemList = response.contacts.map { contact ->
                    ContactItem(
                        id = contact.id,
                        name = contact.name ?: "",
                        career = contact.career ?: "",
                        avatarURL = contact.image ?: ""
                    )
                }

                setState { copy(contactList = contactItemList) }
            },
            processBackendException = {},
            processResponseProcessingException = {},
            processConnectionException = {},
            finally = { setState { copy(isProgressBarShowed = false) } }
        )
    }

    private fun deleteContactItem(contactItem: ContactItem, position: Int) {
        processNetworkExceptions(
            toExecute = {
                serviceProviderHolder.serviceProvider.getContactsService().deleteContact(
                    ContactData(userDataProvider.getUserServerId(), contactItem.id)
                )
                loadContacts()
            },
            processBackendException = {},
            processResponseProcessingException = {},
            processConnectionException = {},
            finally = { })
        deletedItems.push(Pair(contactItem, position))
    }

    private fun deleteListOfContactItems(contactItems: List<ContactItem>) {
        processNetworkExceptions(
            toExecute = {
                coroutineScope {
                    val jobs = contactItems.map { contactItem ->
                        async {
                            serviceProviderHolder.serviceProvider.getContactsService()
                                .deleteContact(
                                    ContactData(userDataProvider.getUserServerId(), contactItem.id)
                                )
                        }
                    }
                    jobs.awaitAll()
                }
                loadContacts()

            },
            processBackendException = {},
            processResponseProcessingException = {},
            processConnectionException = {},
            finally = { })
    }

    private fun addContactItem(contactItem: ContactItem, position: Int) {
        processNetworkExceptions(
            toExecute = {
                serviceProviderHolder.serviceProvider.getContactsService().addContact(
                    ContactData(userDataProvider.getUserServerId(), contactItem.id)
                )
                loadContacts()
            },
            processBackendException = {},
            processResponseProcessingException = {},
            processConnectionException = {},
            finally = { })
    }

    private fun navigateToDetailsScreen(contact: ContactItem) {
        setEffect(ContactListContract.Effect.NavigateToDetailsScreen(contact))
    }

    private fun navigateToPreviousScreen() {
        setEffect(ContactListContract.Effect.NavigateToUserProfileScreen)
    }

    private fun navigateToAddContactsScreen() {
        setEffect(ContactListContract.Effect.NavigateToAddContactsScreen)
    }
}

