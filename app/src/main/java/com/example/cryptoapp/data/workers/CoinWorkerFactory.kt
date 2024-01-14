package com.example.cryptoapp.data.workers

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class CoinWorkerFactory @Inject constructor(
    private val workerProviders: @JvmSuppressWildcards Map <Class<out ListenableWorker>, Provider<ChildWorkerFactory>>
): WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        Log.d("WorkerFactoryTag", "done1")
        return when(workerClassName) {
            RefreshDataWorker::class.qualifiedName -> {
                Log.d("WorkerFactoryTag", "done")
                val childWorkerFactory = workerProviders[RefreshDataWorker::class.java]?.get()
                return childWorkerFactory?.create(appContext, workerParameters)
            }
            else -> null
        }
    }
}