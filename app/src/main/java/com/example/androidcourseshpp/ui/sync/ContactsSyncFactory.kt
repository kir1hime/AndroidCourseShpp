package com.example.androidcourseshpp.ui.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsUseCase
import jakarta.inject.Inject

class ContactsSyncFactory @Inject constructor(
    private val syncContactsUseCase: SyncContactsUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = ContactsSyncWorker(
        syncContactsUseCase = syncContactsUseCase,
        context = appContext,
        workerParameters = workerParameters
    )
}