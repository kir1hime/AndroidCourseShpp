package com.example.androidcourseshpp.ui.sync

import android.content.Context

import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsFromRemoteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UploadContactsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val syncContactsFromRemoteUseCase: SyncContactsFromRemoteUseCase,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val result = syncContactsFromRemoteUseCase()
        return when (result) {
            is com.example.androidcourseshpp.domain.utils.Result.Success -> Result.success()
            is com.example.androidcourseshpp.domain.utils.Result.Error -> Result.retry()
        }
    }
}