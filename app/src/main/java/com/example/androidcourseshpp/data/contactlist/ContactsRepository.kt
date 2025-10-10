package com.example.androidcourseshpp.data.contactlist

import android.content.ContentResolver
import android.provider.ContactsContract
import com.github.javafaker.Faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

private val javaFaker = Faker.instance()
private const val NUM_OF_DEFAULT_CONTACT_ITEMS = 5
private const val NUM_OF_CAREERS = 10

@Singleton
class ContactsRepository @Inject constructor(
      private val contentResolver: ContentResolver
) {

    private val nameList : List<String> = generateNames()

    private val URLImageList = listOf(
        "https://gcs.tripi.vn/public-tripi/tripi-feed/img/474187SoY/anh-avatar-chu-meo-dang-yeu_051724941.jpg",
        "https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg",
        "https://i.pinimg.com/236x/8a/c6/de/8ac6def5cba863e6e187906a7ef04b39.jpg",
        "https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474492fob/avatar-cho-cute_042635954.jpg",
        "https://static.wixstatic.com/media/9d8ed5_4725657bd5b448478d19d54669ea0883~mv2.jpg/v1/fill/w_1000,h_563,al_c,q_85,usm_0.66_1.00_0.01/9d8ed5_4725657bd5b448478d19d54669ea0883~mv2.jpg"
    )

    private val careerList = generateCareers()

    private val _contactList = MutableStateFlow(getContactItems())
    val contactList: StateFlow<List<ContactItem>> get() = _contactList

    fun addContactItem(contactItem: ContactItem, position: Int){
        val currentList = _contactList.value.toMutableList()
        currentList.add(position, contactItem)
        _contactList.value = currentList
    }

    fun addContactItems(contactItems : List<ContactItem>){
        _contactList.value =_contactList.value + contactItems
    }

    fun deleteContactItem(contactItem: ContactItem){
        _contactList.value = _contactList.value - contactItem
    }

    fun deleteContactItems(contactItems: List<ContactItem>){
        _contactList.value = _contactList.value - contactItems
    }


    fun getContactItems(): List<ContactItem> {

        val items = List(nameList.size) { contactId ->
            ContactItem(
                contactId,
                nameList[contactId],
                careerList[contactId % careerList.size],
                URLImageList[contactId % URLImageList.size]
            )
        }

        return items
    }

    fun getContactItemsFromPhoneContacts(lastContactItemId: Int): List<ContactItem> {
        val usersFromPhoneContacts = getUserNamesFromPhoneContacts()

        val items = List(getUserNamesFromPhoneContacts().size) {
            val contactId = it + lastContactItemId + 1
            ContactItem(
                contactId,
                usersFromPhoneContacts[it],
                careerList[contactId % careerList.size],
                URLImageList[contactId % URLImageList.size]
            )
        }

        return items
    }

    private fun getUserNamesFromPhoneContacts(): List<String> {
        val userNames: MutableList<String> = mutableListOf()
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val name =
                    (it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)))
                userNames.add(name)
            }
        }
        return userNames
    }

    private fun generateNames(): List<String> {
        val names: MutableList<String> = mutableListOf()
        repeat(NUM_OF_DEFAULT_CONTACT_ITEMS) {
            names.add(javaFaker.name().name())
        }

        return names
    }

    private fun generateCareers(): List<String> {
        val careers: MutableList<String> = mutableListOf()
        repeat(NUM_OF_CAREERS) {
            careers.add(javaFaker.job().title())
        }

        return careers
    }

}