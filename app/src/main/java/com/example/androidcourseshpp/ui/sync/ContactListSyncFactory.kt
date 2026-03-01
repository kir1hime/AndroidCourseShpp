package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import jakarta.inject.Inject

class ContactListSyncFactory @Inject constructor(
    private val contactsLocalRepository: ContactsLocalRepository,
    private val contactsNetworkRepository: ContactsNetworkRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = ContactListSyncWorker(
        contactsLocalRepository = contactsLocalRepository,
        contactsNetworkRepository = contactsNetworkRepository,
        context = appContext,
        workerParameters = workerParameters
    )
}