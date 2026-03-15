package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
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

    private val workManager = WorkManager.getInstance(context)

    companion object {
        private const val SYNC_FROM_REMOTE = "syncFromRemote"
        private const val SYNC_TO_REMOTE = "syncToRemote"
        private const val BACKOFF_DELAY_IN_SECONDS = 30L
    }

    fun executeOnceSyncToRemote() {
        val workRequest = OneTimeWorkRequestBuilder<PushContactsWorker>()
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = BACKOFF_DELAY_IN_SECONDS,
                TimeUnit.SECONDS
            )
            .setConstraints(
                networkConnectionConstraints
            ).build()

        workManager.enqueueUniqueWork(
            uniqueWorkName = SYNC_TO_REMOTE,
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }


    fun executeOnceSyncFromRemote() {
        val workRequest = OneTimeWorkRequestBuilder<UploadContactsWorker>()
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = BACKOFF_DELAY_IN_SECONDS,
                TimeUnit.SECONDS
            ).setConstraints(
                networkConnectionConstraints
            ).build()

        workManager.enqueueUniqueWork(
            uniqueWorkName = SYNC_FROM_REMOTE,
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}