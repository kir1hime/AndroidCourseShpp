package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.androidcourseshpp.domain.entity.contact.SyncAction
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.utils.onError
import com.example.androidcourseshpp.domain.utils.onSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class ContactListSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val contactsLocalRepository: ContactsLocalRepository,
    private val contactsNetworkRepository: ContactsNetworkRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val result = contactsLocalRepository.getContacts().first()

        if (result is com.example.androidcourseshpp.domain.utils.Result.Success) {
            val contacts = result.data
            var isAllContactsSynced = true

            contacts.filter { it.syncState != SyncAction.SYNCED }.forEach { contact ->
                val contactId = contact.contactInfo.id

                when (contact.syncState) {
                    SyncAction.ADDED -> {
                        contactsNetworkRepository.addContact(contactId).onSuccess {
                            contactsLocalRepository.setSyncStateToContact(
                                contactId,
                                SyncAction.SYNCED
                            )
                        }.onError { isAllContactsSynced = false }
                    }

                    SyncAction.DELETED -> {
                        contactsNetworkRepository.deleteContact(contactId).onSuccess {
                            contactsLocalRepository.deleteContactById(contactId)
                        } .onError { isAllContactsSynced = false }
                    }

                    else -> return@forEach
                }
            }
            return if (isAllContactsSynced) Result.success() else Result.failure()
        } else {
            return Result.failure()
        }
    }
}