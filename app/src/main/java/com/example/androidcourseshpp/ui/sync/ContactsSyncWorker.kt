package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ContactsSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val syncContactsUseCase: SyncContactsUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return when (syncContactsUseCase()) {
            is com.example.androidcourseshpp.domain.utils.Result.Success -> Result.success()
            is com.example.androidcourseshpp.domain.utils.Result.Error -> Result.failure()
        }
    }
}