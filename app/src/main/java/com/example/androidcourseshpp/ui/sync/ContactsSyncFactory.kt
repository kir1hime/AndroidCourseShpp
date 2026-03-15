package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsFromRemoteUseCase
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsToRemoteUseCase
import jakarta.inject.Inject

class ContactsSyncFactory @Inject constructor(
    private val syncContactsToRemoteUseCase: SyncContactsToRemoteUseCase,
    private val syncContactsFromRemoteUseCase: SyncContactsFromRemoteUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            PushContactsWorker::class.java.name -> {
                PushContactsWorker(
                    syncContactsToRemoteUseCase = syncContactsToRemoteUseCase,
                    context = appContext,
                    workerParameters = workerParameters
                )
            }

            UploadContactsWorker::class.java.name -> {
                UploadContactsWorker(
                    syncContactsFromRemoteUseCase = syncContactsFromRemoteUseCase,
                    context = appContext,
                    workerParameters = workerParameters
                )
            }

            else -> null
        }
    }
}