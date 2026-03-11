package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsSyncScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    private val networkConnectionConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    companion object {
        private const val WORK_NAME = "contacts_sync"
    }

     fun executePeriodicSync() {
        val workRequest = PeriodicWorkRequestBuilder<ContactsSyncWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).setConstraints(networkConnectionConstraints).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}