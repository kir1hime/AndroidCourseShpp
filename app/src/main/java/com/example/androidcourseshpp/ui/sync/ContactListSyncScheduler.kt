package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactListSyncScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    private val networkConnectionConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresCharging(true)
        .build()

    companion object {
        private const val WORK_NAME = "contacts_sync"
    }

    fun executePeriodicSync() {
        val workRequest = PeriodicWorkRequestBuilder<ContactListSyncWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).setConstraints(networkConnectionConstraints).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun executeOnceSync() {
        val workRequest = OneTimeWorkRequestBuilder<ContactListSyncWorker>()
            .setConstraints(networkConnectionConstraints).build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

}