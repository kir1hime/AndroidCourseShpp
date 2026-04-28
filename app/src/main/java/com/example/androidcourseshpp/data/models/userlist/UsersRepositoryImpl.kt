package com.example.androidcourseshpp.data.models.userlist

import com.example.androidcourseshpp.data.network.entity.User
import com.example.androidcourseshpp.data.network.entity.contacts.ContactData
import com.example.androidcourseshpp.data.network.service.contacts.ContactsService
import com.example.androidcourseshpp.data.network.service.user.UserService
import com.example.androidcourseshpp.data.userdata.UserDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val contactsService: ContactsService,
    private val userService: UserService,
    private val userDataProvider: UserDataProvider
) : UsersRepository {


    override suspend fun addContact(userItem: UserItem) {
        withContext(Dispatchers.IO) {
            contactsService.addContact(ContactData(userDataProvider.getUserServerId(), userItem.id))
        }
    }

    override suspend fun loadUsers(): List<UserItem> {
        var userList = emptyList<User>()
        var contactList = emptyList<User>()

        withContext(Dispatchers.IO) {
            val usersResponse = async {
               userService.getUsers()
            }
            val contactsResponse = async {
                contactsService
                    .getUserContacts(userDataProvider.getUserServerId())
            }
            userList = usersResponse.await().users
            contactList = contactsResponse.await().contacts
        }

        val userItemList = userList.map { user ->
            UserItem(
                id = user.id,
                name = user.name ?: "",
                career = user.career ?: "",
                avatarURL = user.image ?: "",
                isContact = contactList.contains(user)
            )
        }

        return userItemList
    }
}