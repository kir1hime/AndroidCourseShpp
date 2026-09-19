package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
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
        private const val SYNC_QUEUE = "syncQueue"
        private const val BACKOFF_DELAY_IN_SECONDS = 30L
    }

    fun executeOnceSyncToRemote() {
        val workRequest = OneTimeWorkRequestBuilder<PushContactsWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                BACKOFF_DELAY_IN_SECONDS,
                TimeUnit.SECONDS
            )
            .setConstraints(networkConnectionConstraints)
            .build()

        workManager.enqueueUniqueWork(
            SYNC_QUEUE,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            workRequest
        )
    }

    suspend fun executeObservableOnceSyncFromRemote(): Flow<SyncState> {
        val workRequest = OneTimeWorkRequestBuilder<UploadContactsWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                BACKOFF_DELAY_IN_SECONDS,
                TimeUnit.SECONDS
            )
            .setConstraints(networkConnectionConstraints)
            .build()

        workManager.enqueueUniqueWork(
            SYNC_QUEUE,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            workRequest
        ).await()

        return workManager
            .getWorkInfoByIdFlow(workRequest.id)
            .filterNotNull()
            .map { info ->
                when (info.state) {
                    WorkInfo.State.ENQUEUED,
                    WorkInfo.State.RUNNING,
                    WorkInfo.State.BLOCKED -> SyncState.Waiting

                    WorkInfo.State.SUCCEEDED -> SyncState.Success

                    WorkInfo.State.FAILED,
                    WorkInfo.State.CANCELLED -> SyncState.Failed
                }
            }
            .distinctUntilChanged()
    }

    sealed interface SyncState {
        data object Waiting : SyncState
        data object Success : SyncState
        data object Failed : SyncState
    }
}