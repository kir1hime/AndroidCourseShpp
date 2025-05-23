package com.example.androidcourseshpp.data.contactlistdata

import android.content.ContentResolver
import android.provider.ContactsContract
import com.github.javafaker.Faker

class ContactListGenerator(
    private val contentResolver: ContentResolver,
    private val isAccessToContactsAllowed: Boolean
) {

    private companion object {
        private val javaFaker = Faker.instance()
        private const val NUM_OF_DEFAULT_CONTACT_ITEMS = 5
        private const val NUM_OF_CAREERS = 10
    }

    private val URLImageList = listOf(
        "https://gcs.tripi.vn/public-tripi/tripi-feed/img/474187SoY/anh-avatar-chu-meo-dang-yeu_051724941.jpg",
        "https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg",
        "https://i.pinimg.com/236x/8a/c6/de/8ac6def5cba863e6e187906a7ef04b39.jpg",
        "https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474492fob/avatar-cho-cute_042635954.jpg",
        "https://static.wixstatic.com/media/9d8ed5_4725657bd5b448478d19d54669ea0883~mv2.jpg/v1/fill/w_1000,h_563,al_c,q_85,usm_0.66_1.00_0.01/9d8ed5_4725657bd5b448478d19d54669ea0883~mv2.jpg"
    )

    private var nameList = generateNames()

    private val careerList = generateCareers()

    fun getContactItems(): List<ContactItem> {

        if (isAccessToContactsAllowed) {
            nameList.addAll(getUserNamesFromPhoneContacts())
        }

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

    private fun generateNames() : MutableList<String> {
        val names : MutableList<String> = mutableListOf()
       repeat(NUM_OF_DEFAULT_CONTACT_ITEMS){
           names.add(javaFaker.name().name())
       }

        return names
    }

    private fun generateCareers() : MutableList<String>{
        val careers: MutableList<String> = mutableListOf()
        repeat(NUM_OF_CAREERS){
            careers.add(javaFaker.job().title())
        }

        return careers
    }

}